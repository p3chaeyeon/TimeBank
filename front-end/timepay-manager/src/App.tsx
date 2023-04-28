import React from 'react';
import { ConfigProvider } from 'antd';
import { RecoilRoot } from 'recoil';
import { customTheme } from './styles/constants/customTheme';
import { PageRoutes } from './pages/PageRoutes';

function App() {
  return (
    <RecoilRoot>
      <ConfigProvider theme={customTheme}>
        <PageRoutes />
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;
