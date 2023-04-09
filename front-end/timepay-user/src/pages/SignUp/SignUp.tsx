import { useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import finishBtn from '../../assets/images/finishBtn.svg';
// import KaKaoImg from '../../assets/images/kakao_login_large_wide.svg'
import { PATH } from '../../utils/paths';

const SignUp = () => {
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

  const onChangeTest = ()=> {
    console.log()
  }

  return (
    <>
      <div className = "sign-up">
        <span className='title'>이름과 핸드폰 번호를 입력해주세요</span>
        <div className="info-box">
            <label>이름</label>
            <input type="text" onChange={onChangeTest}/>
            <label style={{marginTop:"10px"}}>핸드폰 번호</label>
            <input type="text" onChange={onChangeTest} />
        </div>
        <div className='finish-btn' onClick = { ()=>handleOnClickLinkBtn(PATH.MAIN) }>
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
