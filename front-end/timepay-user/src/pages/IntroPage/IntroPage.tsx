import { useEffect, useCallback } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import MainImg from '../../assets/images/intro_page.svg';
import Logo from '../../assets/images/timepay_logo.svg';
import KaKaoImg from '../../assets/images/kakao_login_large_wide.svg'
import { PATH } from '../../utils/paths';

const kakaoLogin = () => {
  window.Kakao.Auth.login({
    scope: "profile_nickname, profile_image",
    success: function (authObj) {
      console.log(authObj);
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: (res) => {
          const kakao_account = res.kakao_account;
          console.log(kakao_account);
          window.localStorage.setItem(
            "access_token",
            Kakao.Auth.getAccessToken()
          );
          window.location.href = "./SignUp"; //리다이렉트 되는 코드
        },
      });
    },
    fail: function (error) {
      console.log(error);
    },
  });
};

const IntroPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });

  return (
    <>
      <div className="intro-page">
        <div className="top">
          <img src={Logo} alt="" />
          시간은행
        </div>
        <div className="main-title">
          시간을
          <br />
          저축할 수 있다면
        </div>
        <img src={MainImg} alt="" className="main-img" />
        <div className="kakao-img" onClick={kakaoLogin}>
          {" "}
          {/* kakaologin */}
          <img src={KaKaoImg} alt="" />
        </div>
      </div>
    </>
  );
};

export default IntroPage;
