import { css } from '@emotion/react';

export const baseMenu = css`
  padding: 20px;
  display: flex;
  justify-content: space-between;
  border-bottom: none;
  margin-bottom: 0%;

  .menu-layer {
    top: 1px;
    justify-content: space-between;
    position: relative;
    padding-right: 10px;
  }

  .menu-icon {
    float: left;
  }

  .settings {
    float: right;
  }
  .custom-menu-tooltip .ant-tooltip-inner {
    background-color: white;
    border: 1px solid #cdcdcd;
  }
  .custom-menu-tooltip .ant-tooltip-arrow {
    border-bottom-color: #cdcdcd;
  }
`;

export const menuList = css`
  display: flex;
  justify-content: space-between;
`;



