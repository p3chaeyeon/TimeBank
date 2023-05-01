import { MenuProps } from 'antd';
import { Dropdown, Menu, Tooltip } from 'antd';
import IconGear from '../../assets/images/icon-gear.svg';
import MenuBar from '../../assets/images/menu_bar.svg';
import { baseMenu } from './BaseMenu.styles';
import { Link } from 'react-router-dom';

const items = [
  {
    label: <Link to="/inquiry">문의하기</Link>,
    key: 'inquiry',
  },
  {
    label: <Link to="/inquiry-details">문의내역</Link>,
    key: 'inquiry-details',
  },
  {
    label: <Link to="/unregistal">탈퇴하기</Link>,
    key: 'unregistal',
  },
];

export const BaseMenu = () => {
  return (
    <div css={baseMenu}>
      <Dropdown
        trigger={['hover']}
        overlay={
          <Menu>
            {items.map(item => (
              <Menu.Item key={item.key}>{item.label}</Menu.Item>
            ))}
          </Menu>
        }
      >
        <div className="menu-icon">
          <Tooltip>
            <div>
              <img src={MenuBar} alt="" />
              메뉴
            </div>
          </Tooltip>
        </div>
      </Dropdown>
    </div>
  );
};

export default BaseMenu;
