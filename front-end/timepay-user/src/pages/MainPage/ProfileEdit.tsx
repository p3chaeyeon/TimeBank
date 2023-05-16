import { useEffect, useState, useCallback, useRef } from "react";
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
  const [dotStyle, setDotStyle] = useState(false);

  const [imageSrc, setImageSrc]: any = useState(null);

  const onUpload = (e: any) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);

    return new Promise<void>((resolve) => {
      reader.onload = () => {
        setImageSrc(reader.result || null); // 파일의 컨텐츠
        resolve();
      };
    });
  };

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
                // src={imageSrc}
                src={Profile}
                alt=""
                style={{ top: "3px", position: "relative", width: "30px" }}
              />
              {/* <label htmlFor="file" className="input-file-button"></label> 
              <input type="file" id="file" accept="image/*" onChange={e => onUpload(e)} style={{display:"none"}}></input> */}
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
            이름<input type="text" placeholder="이름" />
            </div>
            <div className="phone">
            전화번호<input type="text" placeholder=" 전화번호" />
            </div>
            <div className="gender">
            성별<input type="text" placeholder="성별" />
            </div>
            <div className="birth-date">
            생년월일 <input type="text" placeholder="생년월일" />
            </div>
          </div>

          <div className="user-account">
            <div>
              계좌번호
              <span
                style={{
                  fontSize: "16px",
                  color: "#787878",
                  marginLeft: "25px",
                }}
              >
                00-00-00
              </span>
              <span
                style={{ float: "right", fontSize: "15px", color: "#F1AF23" }}
                onClick={() => {
                  setOpen(true);
                  setDotStyle(false);
                }}
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
                        <div style={{ padding: "20px 30px" }}>
                          <span
                            style={{
                              fontFamily: "Lato",
                              fontSize: "22px",
                              fontWeight: "500",
                            }}
                          >
                            현재 비밀번호
                          </span>
                        </div>
                        <input
                          onChange={(e) => setCurrentPassWord(e.target.value)}
                          placeholder="현재 비밀번호를 입력해주세요."
                          type="password"
                          maxLength={4}
                          value={currentPassWord || ""}
                          className={dotStyle ? "inputPass" : "inputBox"}
                          onFocus={(e) => {
                            e.target.placeholder = "";
                            setDotStyle(true);
                          }}
                        />
                        <div
                          className="finish-btn"
                          style={{ textAlign: "center" }}
                        >
                          <button
                            style={{
                              marginTop: "60%",
                              backgroundColor: "#f1af23",
                              color: "#fff",
                              padding: "13px 35px",
                              borderRadius: "35px",
                              fontFamily: "Lato",
                              fontSize: "20px",
                              boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
                              fontWeight: "700",
                            }}
                            onClick={() => {
                              setCheckPassWordModal(false);
                            }}
                          >
                            다음
                          </button>
                        </div>
                      </div>
                    ) : (
                      <div
                        id="change_password_modal_sheet"
                        style={{ display: "flex", flexDirection: "column" }}
                      >
                        <div style={{ padding: "20px 30px" }}>
                          <label
                            style={{
                              fontFamily: "Lato",
                              fontSize: "22px",
                              fontWeight: "500",
                              lineHeight: "2em",
                            }}
                          >
                            변경할 비밀번호
                            <input
                              type="password"
                              value={password}
                              maxLength={4}
                              onChange={(e) => {
                                setPassWord(e.target.value);
                              }}
                              className={dotStyle ? "inputPass" : "inputBox"}
                              onFocus={(e) => {
                                e.target.placeholder = "";
                                setDotStyle(true);
                              }}
                              style={{ position: "relative" }}
                            />
                          </label>
                        </div>

                        <div style={{ padding: "20px 30px" }}>
                          <label
                            style={{
                              fontFamily: "Lato",
                              fontSize: "22px",
                              fontWeight: "500",
                              lineHeight: "2em",
                            }}
                          >
                            변경할 비밀번호 확인
                            <input
                              type="password"
                              maxLength={4}
                              value={passwordCert}
                              onChange={(e) => {
                                setPassWordCert(e.target.value);
                              }}
                              className={dotStyle ? "inputPass" : "inputBox"}
                              onFocus={(e) => {
                                e.target.placeholder = "";
                                setDotStyle(true);
                              }}
                              style={{ position: "relative" }}
                            />
                          </label>
                        </div>
                        {isSamePassword && (
                          <div
                            style={{
                              textAlign: "center",
                              color: "#FF2E00",
                              fontFamily: "Lato",
                              fontSize: "18px",
                            }}
                          >
                            비밀번호 오류. 다시 입력해주세요.
                          </div>
                        )}
                        <button
                          style={{
                            width: "fit-content",
                            margin: "0 auto",
                            position: "relative",
                            top: "45px",
                            backgroundColor: "#f1af23",
                            color: "#fff",
                            padding: "13px 35px",
                            borderRadius: "35px",
                            fontFamily: "Lato",
                            fontSize: "20px",
                            boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
                            fontWeight: "700",
                          }}
                          onClick={() => {
                            handleOnClickChangePasswordBtn(
                              password,
                              passwordCert
                            );
                            // setDotStyle(false);
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
