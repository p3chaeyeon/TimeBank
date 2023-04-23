import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

const HomePage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle(null);
  });
  return <div>홈페이지</div>; 
};

export default HomePage;
