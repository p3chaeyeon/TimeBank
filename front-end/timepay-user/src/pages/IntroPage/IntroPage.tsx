import { useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import Logo from '../../assets/images/Logo0322.svg';
import KaKaoImg from '../../assets/images/kakao_login_large_wide.svg'
import { PATH } from '../../utils/paths';
/*import {KakaoSDK,Kakao} from 'kakao-sdk';

const kakaoLogin = () => {
  KakaoSDK.init('a66d3f28c1e74a0287ef3c99e077e122'); // 발급받은 키 중 javascript키를 사용해준다.

  KakaoSDK.Auth.authorize({
    scope: 'profile_nickname,profile_image',
    success: (authObj: KakaoSDK.Auth.AuthResponse) => {
      console.log(authObj);

      KakaoSDK.API.request({
        url: '/v2/user/me',
        success: (res: any) => {
          const kakao_account = res.kakao_account;
          console.log(kakao_account);
        },
      });
    },
    fail: (err: any) => {
      console.log(err);
    },
  });
};*/
const IntroPage = () => {
  const navigate = useNavigate();

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <>
      <div className = "intro-page">
        <img src={Logo} alt="" className='logo'/>
        <div className='kakao-img' onClick = { ()=>handleOnClickLinkBtn(PATH.SIGN_UP) }>
          <img src={KaKaoImg} alt=""/>
        </div>
      </div>
    </>
  );
};

export default IntroPage;