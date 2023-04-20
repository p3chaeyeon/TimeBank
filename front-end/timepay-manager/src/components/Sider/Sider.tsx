import React, { useMemo } from 'react';
import { Link } from 'react-router-dom';
import { Menu, MenuProps } from 'antd';
import {
  MailOutlined,
  PayCircleOutlined,
  TeamOutlined,
} from '@ant-design/icons';
import { PATH } from '../../constants/path';

type MenuItem = Required<MenuProps>['items'][number];

export function Sider() {
  const items: MenuItem[] = useMemo(() => {
    return [
      {
        type: 'group',
        label: '관리 메뉴',
        children: [
          {
            key: PATH.USER_PAGE,
            label: '사용자 관리',
            icon: (
              <Link to={PATH.USER_PAGE}>
                <TeamOutlined />
              </Link>
            ),
          },
          {
            key: PATH.TRANSFER_PAGE,
            label: '이체 관리',
            icon: (
              <Link to={PATH.TRANSFER_PAGE}>
                <PayCircleOutlined />
              </Link>
            ),
          },
          {
            key: PATH.INQUIRY_PAGE,
            label: '문의 관리',
            icon: (
              <Link to={PATH.INQUIRY_PAGE}>
                <MailOutlined />
              </Link>
            ),
          },
        ],
      },
    ];
  }, []);

  return (
    <Menu
      css={{ height: '100%' }}
      items={items}
      selectedKeys={[window.location.pathname]}
    ></Menu>
  );
}
