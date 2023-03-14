import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssMainFooterStyle = css`
  position: fixed;
  bottom: 0;

  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  height: 80px;
  padding: 0;
  padding-top: 11px;
  padding-bottom: 13px;

  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  gap: 30px;
  box-shadow: 0px -3px 10px rgba(0, 0, 0, 0.05);

  span {
    font-size: 30px;
  }
  .ant-btn {
    width: max-content;
    height: max-content;
    border: 0;
    padding: 0;
    border-radius: 54px;
  }

  .active {
    svg {
      fill: ${COMMON_COLOR.MAIN2};
    }
  }
`;

export const cssPlusPostBtnStyle = css`
  width: 54px !important;
  height: 54px !important;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  display: flex;
  justify-content: center;
  align-items: center;
`;
