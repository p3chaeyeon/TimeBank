import type { MenuProps } from "antd";
import { Dropdown } from "antd";
import MenuBar from "../../assets/images/menu_bar.svg";
import { baseMenu } from "./BaseMenu.styles";
import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { PATH } from "../../utils/paths";

export const MainMenu = () => {
  const navigate = useNavigate();
  const items: MenuProps["items"] = [
    {
      label: (
        <label onClick={() => handleOnClickLinkBtn(PATH.QNAREGISTER)}>
          문의하기
        </label>
      ),
      key: "inquiry",
    },
    {
      label: (
        <label onClick={() => handleOnClickLinkBtn(PATH.QNAMAIN)}>
          문의내역
        </label>
      ),
      key: "inquiry",
    },
    {
      label: (
        <label onClick={() => handleOnClickLinkBtn(PATH.UNREGISTAL)}>
          탈퇴하기
        </label>
      ),
      key: "withdrawal",
    },
  ];

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate]
  );

  return (
    <div css={baseMenu}>
      <div className="menu-layer">
        <div className="menu-icon">
          <Dropdown
            menu={{
              items,
            }}
            trigger={["click"]}
          >
            <div onClick={(e) => e.preventDefault()}>
              <img src={MenuBar} alt="" />
              메뉴
            </div>
          </Dropdown>
        </div>
        <div className="settings"></div>
      </div>
    </div>
  );
};

export default MainMenu;
