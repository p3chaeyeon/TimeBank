import { useEffect, useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import axios from 'axios';
import { PATH } from '../../utils/paths';

async function getTimepayAccessToken(kakaoAccesToken: string) {
  try {
    const response = await axios({
      method: 'POST',
      url: PATH.SERVER + '/api/v1/users/login',
      data: {
        authenticationType: 'social',
        socialPlatformType: 'KAKAO',
        accessToken: kakaoAccesToken,
      },
      headers: {
        Authorization: `Bearer ${kakaoAccesToken}`,
      },
    });

    const data = response.data;
    window.localStorage.setItem('access_token', data.accessToken);
  } catch (e) {
    console.error(e);
  }
}

async function signByUserData(
  name: String,
  phoneNumber: String,
  gender: String,
  birthday: String,
) {
  try {
    const kakaoAccessToken = window.localStorage.getItem('kakao_access_token');
    if (kakaoAccessToken == null) {
      window.location.href = './IntroPage';
      return;
    }

    const response = await axios({
      method: 'POST',
      url: PATH.SERVER + '/api/v1/users/register',
      data: {
        authenticationType: 'social',
        socialPlatformType: 'KAKAO',
        accessToken: kakaoAccessToken,
        name: name,
        phoneNumber: phoneNumber,
        gender: gender,
        birthday: getFormattedBirthday(birthday),
      },
    });

    console.log('signByUserData response : ', response);

    if (response.status === 201) {
      await getTimepayAccessToken(kakaoAccessToken);
      window.location.href = './main';
    }
  } catch (e) {
    console.error(e);
  }
}

export const getFormattedBirthday = (birthday: String) => {
  const year = birthday.slice(0, 4);
  const month = birthday.slice(4, 6);
  const day = birthday.slice(6, 8);
  const formattedDate = `${year}-${month}-${day}`;
  return formattedDate;
};

const SignUp = () => {
  const navigate = useNavigate();
  let [name, setName] = useState('');
  let [phoneNumber, setPhoneNumber] = useState('');
  let [gender, setGender] = useState('MALE');
  let [birthday, setBirthday] = useState('');

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });

  const handleOnClickLinkBtn = useCallback(
    async (
      path: string,
      name: string,
      phoneNumber: string,
      gender: string,
      birthday: string,
    ) => {
      console.log(`${name} ${phoneNumber} ${birthday} ${gender}`);
      if (name.length > 0 && phoneNumber.length > 0 && birthday.length > 0) {
        await signByUserData(name, phoneNumber, gender, birthday);
        navigate(path);
      }
    },
    [navigate],
  );

  const onNumberChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    const onlyNumber = value.replace(/[^0-9]/g, '');
    setPhoneNumber(onlyNumber);
  };

  return (
    <>
      <div className="sign-up">
        <span className="title">회원가입</span>
        <div className="info-box">
          <label>이름</label>
          <input
            type="text"
            placeholder="이름"
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
          <label style={{ marginTop: '10px' }}>전화번호</label>
          <input
            type="text"
            placeholder="숫자 11자리"
            value={phoneNumber}
            maxLength={11}
            onChange={(e) => {
              onNumberChanged(e);
            }}
          />
          <label>성별</label>
          <select
            id="dropdown"
            value={gender}
            onChange={(e) => setGender(e.target.value)}
          >
            <option value="MALE">남성</option>
            <option value="FEMALE">여성</option>
          </select>
          <label style={{ marginTop: '10px' }}>생년월일</label>
          <input
            type="text"
            placeholder="YYYY-MM-DD"
            maxLength={8}
            onChange={(e) => {
              setBirthday(e.target.value);
            }}
          />
        </div>
        <div
          className="finish-btn"
          onClick={() =>
            handleOnClickLinkBtn(PATH.MAIN, name, phoneNumber, gender, birthday)
          }
        >
          <button>가입하기</button>
        </div>
      </div>
    </>
  );
};

export default SignUp;
