import { css } from '@emotion/react';

export const baseMenu = css`
  padding: 20px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #cdcdcd;
  margin-bottom: 20%;

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
`;

export const menuList = css`
  display: flex;
  justify-content: space-between;
`;
