import { useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import Logo from '../../assets/images/Logo0322.svg';
import KaKaoImg from '../../assets/images/kakao_login_large_wide.svg'
import { PATH } from '../../utils/paths';

const IntroPage = () => {
  const navigate = useNavigate();

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  // const handleOnClickLinkBtn = useCallback(
  //   (path: string) => {
  //     navigate(path);
  //   },
  //   [navigate],
  // ); 
  
  const kakaoLogin=()=>{
        window.Kakao.Auth.login({
          scope:'profile_nickname, profile_image',
          success: function(authObj){
            console.log(authObj);
            window.Kakao.API.request({
              url:'/v2/user/me',
              success:res=>{
                const kakao_account=res.kakao_account;
                console.log(kakao_account);
                window.location.href="./SignUp";
              }
            });
          //window.location.href="http://localhost:3000/auth/kakao" //리다이렉트 되는 코드
          },
      fail: function(error) {
          console.log(error);
      }
    })
}

  return (
    <>
      <div className = "intro-page">
        <img src={Logo} alt="" className='logo'/>
        <div className='kakao-img' onClick={kakaoLogin}>
        <img src={KaKaoImg} alt=""/>
        </div>
      </div>
    </>
  );
};
export default IntroPage;
