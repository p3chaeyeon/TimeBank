import { useEffect, useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { headerTitleState } from "../../states/uiState";
import Profile from "../../assets/images/profile.svg";
import Sheet from "react-modal-sheet";
import axios from "axios";
import { PATH } from "../../utils/paths";

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

const ProfileEdit = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [checkPassWordModal, setCheckPassWordModal] = useState(true);
  const [currentPassWord, setCurrentPassWord] = useState("");
  const [password, setPassWord] = useState("");
  const [passwordCert, setPassWordCert] = useState("");
  let [isSamePassword, setIsSamePassword] = useState(false);

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle("내 정보 수정");
  });
  const handleOnClickChangePasswordBtn = useCallback(
    (password: string, passwordCert: string) => {
      if (password === passwordCert) {
        setUserPassWord(password);
        initVariables();
      } else {
        setIsSamePassword(true);
      }
      console.log(isSamePassword);
    },
    [navigate]
  );

  const initVariables = () => {
    setCheckPassWordModal(true);
    setCurrentPassWord("");
    setPassWord("");
    setPassWordCert("");
    setOpen(false);
  };

  return (
    <>
      <div className="unregist-page">
        <div className="edit-body">
          <div className="user-title">
            <div className="circle-profile">
              <img
                src={Profile}
                alt=""
                style={{ top: "3px", position: "relative" }}
              />
            </div>
            <div className="name-title">
              홍길동
              <br />
              <span style={{ color: "#cdcdcd", fontSize: "16px" }}>
                #123456789
              </span>
            </div>
          </div>

          <div className="user-info">
            <div className="name">
              이름 <input type="text" placeholder="이름" />
            </div>
            <div className="gender">
              성별 <input type="text" placeholder="성별" />
            </div>
            <div className="age">
              나이 <input type="text" placeholder="나이" />
            </div>
            <div className="region">
              지역정보{" "}
              <input
                type="text"
                placeholder="지역정보"
                style={{ marginLeft: "18px" }}
              />
            </div>
          </div>

          <div className="user-account">
            <div>
              계좌번호
              <span
                style={{
                  fontSize: "16px",
                  color: "#787878",
                  marginLeft: "10px",
                }}
              >
                000-000-0000
              </span>
              <span
                style={{ float: "right", fontSize: "13px", color: "#F1AF23" }}
                onClick={() => setOpen(true)}
              >
                비밀번호 변경
              </span>
              <Sheet
                snapPoints={[(window.innerHeight / 5) * 3, 0]}
                initialSnap={0}
                isOpen={open}
                onClose={() => initVariables()}
              >
                <Sheet.Container>
                  <Sheet.Header />
                  <Sheet.Content>
                    {checkPassWordModal ? (
                      <div id="check_password_modal_sheet">
                        <div>
                          <span>현재 비밀번호</span>
                        </div>
                        <input
                          onChange={(e) => setCurrentPassWord(e.target.value)}
                          placeholder="현재 비밀번호를 입력해주세요."
                          type="password"
                          maxLength={4}
                          value={currentPassWord || ""}
                          className="inputBox"
                        />
                        <button
                          style={{ marginTop: "50%" }}
                          onClick={() => setCheckPassWordModal(false)}
                        >
                          다음
                        </button>
                      </div>
                    ) : (
                      <div id="change_password_modal_sheet">
                        <div>
                          <text>변경할 비밀번호</text>
                        </div>
                        <input
                          type="password"
                          value={password}
                          maxLength={4}
                          onChange={(e) => {
                            setPassWord(e.target.value);
                          }}
                        />
                        <div>
                          <label style={{ marginTop: "10px" }}>
                            변경할 비밀번호 확인
                          </label>
                        </div>
                        <input
                          type="password"
                          maxLength={4}
                          value={passwordCert}
                          onChange={(e) => {
                            setPassWordCert(e.target.value);
                          }}
                        />
                        {isSamePassword && (
                          <label>비밀번호 오류. 다시 입력해주세요.</label>
                        )}
                        <button
                          style={{ marginTop: "50%" }}
                          onClick={() => {
                            handleOnClickChangePasswordBtn(
                              password,
                              passwordCert
                            );
                          }}
                        >
                          확인
                        </button>
                      </div>
                    )}
                  </Sheet.Content>
                </Sheet.Container>

                <Sheet.Backdrop />
              </Sheet>
            </div>
          </div>
        </div>
        <div className="finish-btn" style={{ textAlign: "center" }}>
          <button style={{ marginTop: "50%" }}>수정완료</button>
        </div>
      </div>
    </>
  );
};

export default ProfileEdit;
