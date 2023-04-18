import BaseLayout from './components/BaseLayout';
import { Route, Routes } from 'react-router-dom';
import React from 'react';
import { ConfigProvider } from 'antd';
import { customTheme } from './styles/constants/customTheme';
import PageRoutes from './pages';
import { PATH } from './utils/paths';
import { RecoilRoot } from 'recoil';

function App() {
  return (
    <RecoilRoot>
      <ConfigProvider theme={customTheme}>
        <Routes>
          <Route element={<BaseLayout />}>
          <Route path={PATH.HOME} element={<PageRoutes.IntroPage />} />
          <Route path={PATH.SIGN_UP} element={<PageRoutes.SignUp />} />
          <Route path={PATH.MAIN} element={<PageRoutes.UserMainPage />} />
          <Route path={PATH.PASSWORD} element={<PageRoutes.Password />} />
          <Route path={PATH.TRANSFER} element={<PageRoutes.Transfer/>} />
          <Route path={PATH.TRANSFERAMOUNT} element={<PageRoutes.TransferAmt/>} />
          <Route path={PATH.TRANSFERPASSWORD} element={<PageRoutes.TransferPW/>} />
          <Route path={PATH.TRANSFERLOG} element={<PageRoutes.TransferLog/>} />
          <Route path={PATH.QNAREGISTER} element={<PageRoutes.QnaRegister/>} />
          <Route path={PATH.QNAMAIN} element={<PageRoutes.QnaLogMain/>} />
          <Route path={PATH.QNADETAIL} element={<PageRoutes.QnaLogDetail/>} />
            {/* <Route path={PATH.HOME} element={<PageRoutes.HomePage />} />
            <Route path={PATH.SEARCH} element={<PageRoutes.SearchPage />} />
            <Route path={PATH.MY_PAGE} element={<PageRoutes.MyPage />} /> */}
          </Route>
        </Routes>
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;