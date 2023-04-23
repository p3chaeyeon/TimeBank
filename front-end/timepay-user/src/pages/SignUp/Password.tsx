import { useEffect, useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { headerTitleState } from "../../states/uiState";
import axios from "axios";
import { PATH } from "../../utils/paths";

async function signByUserData(name: String, phoneNumber: String) {
  try { 
    const access_token = window.localStorage.getItem("access_token");
    await axios.post(
      PATH.SERVER + "/api/v1/users/register",
      {
        provider: "KAKAO",
        accessToken: access_token,
        name: name,
        phoneNumber: phoneNumber,
      }
    ).then((res) => {
      const { data: { accessToken: timepayAccessToken} } = res;
      window.localStorage.setItem(
        "timepay_accesstoken",
        timepayAccessToken
      );
    });
  } catch (e) {
    console.error(e);
  }
}

const SignUp = () => {
  const navigate = useNavigate();
  let [name, setName] = useState("");
  let [phoneNumber, setPhoneNumber] = useState("");

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });

  const handleOnClickLinkBtn = useCallback(
    (path: string, name: string, phoneNumber: string) => {
      console.log("name :" + name + ", phonNumber: " + phoneNumber);
      signByUserData(name, phoneNumber);
      navigate(path);
    },
    [navigate]
  );

  const onNumberChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    const onlyNumber = value.replace(/[^0-9]/g, "");
    console.log(onlyNumber);
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
          <label style={{ marginTop: "10px" }}>핸드폰 번호</label>
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
          onClick={() => handleOnClickLinkBtn(PATH.PASSWORD, name, phoneNumber)}
        >
          <button>가입하기</button>
        </div>
        {/* <div className='finish-btn' onClick = { ()=>handleOnClickLinkBtn(PATH.SIGN_UP) }>
            <img src={finishBtn} alt=""/>
        </div> */}
      </div>
    </>
  );
};

export default SignUp;
