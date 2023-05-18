import { useEffect, useState, useCallback, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { headerTitleState } from "../../states/uiState";
import Profile from "../../assets/images/profile.svg";
import Sheet from "react-modal-sheet";
import axios from "axios";
import { PATH } from "../../utils/paths";
import { getFormattedBirthday } from "../SignUp/SignUp";

const ProfileEdit = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [checkPassWordModal, setCheckPassWordModal] = useState(true);
  const [currentPassWord, setCurrentPassWord] = useState("");
  const [password, setPassWord] = useState("");
  const [passwordCert, setPassWordCert] = useState("");
  const [dotStyle, setDotStyle] = useState(false);
  const [name, setName] = useState("이름");
  const [phoneNumber, setPhoneNumber] = useState(" 전화번호");
  const [gender, setGender] = useState("성별");
  const [birthday, setBirthday] = useState("생년월일");
  const [accountNumber, setAccountNumber] = useState("현재 계좌 없음");
  const access_token = window.localStorage.getItem("access_token");
  const [imageSrc, setImageSrc]: any = useState(null);
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

  const handleOnClickUpdateBtn = useCallback(
    async (
      name: string,
      phoneNumber: string,
      gender: string,
      birthday: string
    ) => {
      await updateUserProfile(name, phoneNumber, gender, birthday);
      navigate(PATH.MAIN);
    },
    [navigate]
  );

  async function getUserProfile() {
    try {
      await axios({
        method: "GET",
        url: PATH.SERVER + "/api/v1/users/me",
        headers: {
          Authorization: `Bearer ${access_token}`,
        },
      }).then((res) => {
        console.log("getUserProfile status code : " + res.status);
        setName(res.data.name);
        setGender(res.data.gender);
        setPhoneNumber(res.data.phoneNumber);
        setBirthday(res.data.birthday.replaceAll("-", ""));
      });
    } catch (e) {
      console.error(e);
    }
  }

  async function setUserPassWord(password: string) {
    try {
      await axios({
        method: "PUT",
        url: PATH.SERVER + "/api/v1/users/me",
        headers: {
          Authorization: `Bearer ${access_token}`,
        },
        data: {
          name: name,
          phoneNumber: phoneNumber,
          gender: gender,
          birthday: getFormattedBirthday(birthday),
        },
      }).then((res) => {
        console.log("status code : " + res.status);
      });
    } catch (e) {
      console.error(e);
    }
  }

  async function updateUserProfile(
    name: string,
    phoneNumber: string,
    gender: string,
    birthday: string
  ) {
    try {
      await axios({
        method: "PUT",
        url: PATH.SERVER + "/api/v1/users/me",
        headers: {
          Authorization: `Bearer ${access_token}`,
        },
        data: {
          name: name,
          phoneNumber: phoneNumber,
          gender: gender,
          birthday: getFormattedBirthday(birthday),
        },
      }).then((res) => {
        console.log("updateUserProfile status code : " + res.status);
      });
    } catch (e) {
      console.error(e);
    }
  }

  async function getUserAccount() {
    try {
      const accessToken = window.localStorage.getItem("access_token");
      await axios({
        method: "GET",
        url: PATH.SERVER + "/api/v1/bank/account",
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }).then((res) => {
        console.log(
          `getUserAccount status code : ${res.status}\ndata : ${res.data}`
        );
        let index = 0;
        res.data.map((account: any) => {
          if (index > 0) return;
          setAccountNumber(account.bankAccountNumber);
          index++;
        });
      });
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  const initVariables = () => {
    setCheckPassWordModal(true);
    setCurrentPassWord("");
    setPassWord("");
    setPassWordCert("");
    setOpen(false);
  };

  getUserProfile();
  getUserAccount();

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
              {name}
              <br />
              <span style={{ color: "#cdcdcd", fontSize: "16px" }}>
                #123456789
              </span>
            </div>
          </div>

          <div className="user-info">
            <div className="phone">
              전화번호
              <input
                type="text"
                maxLength={11}
                placeholder={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </div>
            <div className="gender">
              성별
              <select id="dropdown" onChange={(e) => setGender(e.target.value)}>
                <option value="MALE">남성</option>
                <option value="FEMALE">여성</option>
              </select>
            </div>
            <div className="birth-date">
              생년월일{" "}
              <input
                type="text"
                placeholder={birthday}
                maxLength={8}
                onChange={(e) => setBirthday(e.target.value)}
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
                  marginLeft: "25px",
                }}
              >
                {accountNumber}
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
          <button
            style={{ marginTop: "50%" }}
            onClick={() =>
              handleOnClickUpdateBtn(name, phoneNumber, gender, birthday)
            }
          >
            수정완료
          </button>
        </div>
      </div>
    </>
  );
};

export default ProfileEdit;
