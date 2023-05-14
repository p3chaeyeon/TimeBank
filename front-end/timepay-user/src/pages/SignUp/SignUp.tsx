import { useEffect, useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import axios from 'axios';
import { PATH } from '../../utils/paths';

async function signByUserData(name: String, phoneNumber: String) {
  try {
    const access_token = window.localStorage.getItem('access_token')
      ? window.localStorage.getItem('access_token')
      : '';
    if (access_token !== '') {
      await axios
        .post(PATH.SERVER + '/api/v1/users/register', {
          authenticationType: "social",
          socialPlatformType: "KAKAO",
          accessToken: access_token,
          name: name,
          phoneNumber: phoneNumber,
        })
        .then((res) => {
          console.log('status code : ' + res.status);
        });
    }
  } catch (e) {
    console.error(e);
  }
}

const SignUp = () => {
  const navigate = useNavigate();
  let [name, setName] = useState('');
  let [phoneNumber, setPhoneNumber] = useState('');

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });

  const handleOnClickLinkBtn = useCallback(
    async (path: string, name: string, phoneNumber: string) => {
      await signByUserData(name, phoneNumber);
      navigate(path);
  
      setTimeout(() => {
        navigate('/password');
      }, 3000);
    },
    [navigate]
  );

  const onNumberChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    const onlyNumber = value.replace(/[^0-9]/g, '');
    setPhoneNumber(onlyNumber);
  };

  return (
    <>
      <div className="sign-up">
        <span className="title">이름과 핸드폰 번호를 입력해주세요</span>
        <div className="info-box">
          <label>이름</label>
          <input
            type="text"
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
          <label style={{ marginTop: '10px' }}>핸드폰 번호</label>
          <input
            type="text"
            value={phoneNumber}
            maxLength={11}
            onChange={(e) => {
              onNumberChanged(e);
            }}
          />
        </div>
        <div
          className="finish-btn"
          onClick={() => handleOnClickLinkBtn(PATH.MAIN, name, phoneNumber)}
        >
          <button>가입하기</button>
        </div>
      </div>
    </>
  );
};

export default SignUp;
