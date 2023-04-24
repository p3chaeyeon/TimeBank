import type { MenuProps } from 'antd';
import { Dropdown, Space } from 'antd';
import IconGear from '../../assets/images/icon-gear.svg';
import MenuBar from '../../assets/images/menu_bar.svg';
import { baseMenu } from './BaseMenu.styles';
import { Link } from 'react-router-dom';

const items: MenuProps['items'] = [
  {
    label: <Link to="/inquiry">문의하기</Link>,
    key: 'inquiry',
  },
  {
    label: <Link to="/inquiry-details">문의내역</Link>,
    key: 'inquiry',
  },
  {
    label: <Link to="/withdrawal">탈퇴하기</Link>,
    key: 'withdrawal',
  },
];

export const MainMenu = () => {
  return (
    <div css={baseMenu}>
      <div className="menu-layer">
        <div className="menu-icon">
          <Dropdown
            menu={{
              items,
            }}
            trigger={['click']}
          >
            <div onClick={(e) => e.preventDefault()}>
              <img src={MenuBar} alt="" />
              메뉴
            </div>
          </Dropdown>
        </div>
        <div className="settings">
          <img src={IconGear} alt="" />
        </div>
      </div>
    </div>
  );
};

export default MainMenu;
