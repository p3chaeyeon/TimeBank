import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

const MyPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('내정보');
  });
  return <div>마이페이지</div>;
}; 

export default MyPage;
