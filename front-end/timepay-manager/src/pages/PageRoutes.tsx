import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { BaseLayout } from '../components/BaseLayout';
import { UserPage } from './UserPage';
import { HomePage } from './HomePage';
import { PATH } from '../constants/path';
import { TransferPage } from './TransferPage';
import { InquiryPage } from './InquiryPage';

export function PageRoutes() {
  return (
    <Routes>
      <Route element={<BaseLayout />}>
        <Route index element={<HomePage />} />
        <Route path={PATH.USER_PAGE} element={<UserPage />} />
        <Route path={PATH.TRANSFER_PAGE} element={<TransferPage />} />
        <Route path={PATH.INQUIRY_PAGE} element={<InquiryPage />} />
      </Route>
    </Routes>
  );
}
