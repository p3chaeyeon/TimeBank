import { useEffect, useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { headerTitleState } from "../../states/uiState";
import { PATH } from "../../utils/paths";
import axios from "axios";

async function setUserPassWord(password: string) {
  try {
    const timepayAccessToken = window.localStorage.getItem(
      "timepay_access_token"
    );
    await axios
      .post(PATH.SERVER + "/api/v1/account", {
        accessToken: timepayAccessToken,
        password: password,
      })
      .then((res) => {
        const {
          data: { id: accountId, number: accountNumber, balance: balance },
        } = res;
        window.localStorage.setItem("account_id", accountId);
        window.localStorage.setItem("account_number", accountNumber);
        window.localStorage.setItem("balance", balance);
      });
  } catch (e) {
    console.error(e);
  }
}

const Password = () => {
  const navigate = useNavigate();
  let [password, setPassWord] = useState("");
  let [passwordCert, setPassWordCert] = useState("");
  let [isSamePassword, setIsSamePassword] = useState(false);

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  const handleOnClickLinkBtn = useCallback(
    (path: string, password: string, passwordCert: string) => {
      if (password === passwordCert) {
        setUserPassWord(password);
        navigate(path);
      } else {
        setIsSamePassword(true);
      }
      console.log(isSamePassword);
    },
    [navigate]
  );

  return (
    <>
      <div className="sign-up">
        <span className="title">초기 비밀번호를 설정해주세요</span>
        <div className="info-box">
          <label>초기 비밀번호</label>
          <input
            type="password"
            value={password}
            maxLength={4}
            onChange={(e) => {
              setPassWord(e.target.value);
            }}
          />
          <label style={{ marginTop: "10px" }}>초기 비밀번호 확인</label>
          <input
            type="password"
            maxLength={4}
            value={passwordCert}
            onChange={(e) => {
              setPassWordCert(e.target.value);
            }}
          />
        </div>
        {isSamePassword && <label>비밀번호 오류. 다시 입력해주세요.</label>}
        <div className="finish-btn">
          <button
            onClick={() =>
              handleOnClickLinkBtn(PATH.MAIN, password, passwordCert)
            }
          >
            가입하기
          </button>
        </div>
      </div>
    </>
  );
};

export default Password;
