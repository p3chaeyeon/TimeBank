import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssMainHeaderStyle = css`
  position: fixed;
  top: 0;

  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  height: 70px;

  padding: 25px 0 25px 20px;

  color: ${COMMON_COLOR.MAIN1};
  font-weight: 700;
  font-size: 20px;
  line-height: 15px;
  border: 0;
  border-bottom: 1px solid #cdcdcd;

  display: flex;
  flex-direction: row;
  gap: 10px;
  justify-content: flex-start;
  align-items: center;

  .header-title {
    margin-top: 1px;
  }
`;
