import { useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';

const Password = () => {
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
        <span className='title'>초기 비밀번호를 설정해주세요</span>
        <div className="info-box">
            <label>초기 비밀번호</label>
            <input type="text" onChange={onChangeTest}/>
            <label style={{marginTop:"10px"}}>초기 비밀번호 확인</label>
            <input type="text" onChange={onChangeTest} />
        </div>
        <div className='finish-btn' onClick = { ()=>handleOnClickLinkBtn(PATH.MAIN) }>
            <button>가입하기</button>
        </div>
      </div>
    </>
  );
};

export default Password;
