import {
  ContainerOutlined,
  FrownOutlined,
  MailOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  MessageOutlined,
  NotificationOutlined,
  TeamOutlined,
  UserSwitchOutlined,
} from '@ant-design/icons';
import { Layout, Menu, MenuProps } from 'antd';
import { useMemo, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import { cssBaseLayoutStyle } from './BaseLayout.styles';
import { ReactComponent as Logo } from '../../assets/images/timepay-logo.svg';

const BaseLayout = () => {
  const [collapsed, setCollapsed] = useState(false);

  const items: Required<MenuProps>['items'] = useMemo(() => {
    return [
      {
        key: PATH.POST_MANAGEMENT,
        label: '게시글 관리',
        icon: (
          <Link to={PATH.POST_MANAGEMENT}>
            <ContainerOutlined />
          </Link>
        ),
      },
      {
        key: PATH.COMMENT_MANAGEMENT,
        label: '댓글 관리',
        icon: (
          <Link to={PATH.COMMENT_MANAGEMENT}>
            <MessageOutlined />
          </Link>
        ),
      },
      {
        key: PATH.QNA_MANAGEMENT,
        label: '문의 관리',
        icon: (
          <Link to={PATH.QNA_MANAGEMENT}>
            <MailOutlined />
          </Link>
        ),
      },
      {
        key: PATH.USER_MANAGEMENT,
        label: '회원 관리',
        icon: (
          <Link to={PATH.USER_MANAGEMENT}>
            <TeamOutlined />
          </Link>
        ),
      },
      {
        key: PATH.REPORT_MANAGEMENT,
        label: '신고 관리',
        icon: (
          <Link to={PATH.REPORT_MANAGEMENT}>
            <FrownOutlined />
          </Link>
        ),
      },
      {
        key: PATH.PUSH_MANAGEMENT,
        label: 'PUSH 관리',
        icon: (
          <Link to={PATH.PUSH_MANAGEMENT}>
            <NotificationOutlined />
          </Link>
        ),
      },
      {
        key: PATH.ADMIN_MANAGEMENT,
        label: '관리자 관리',
        icon: (
          <Link to={PATH.ADMIN_MANAGEMENT}>
            <UserSwitchOutlined />
          </Link>
        ),
      },
    ];
  }, []);

  return (
    <Layout css={cssBaseLayoutStyle}>
      <Layout.Sider trigger={null} collapsible collapsed={collapsed}>
        <Logo className={`logo ${collapsed ? 'collapsed' : 'no-collapsed'}`} />
        <Menu
          defaultSelectedKeys={[window.location.pathname]}
          mode="inline"
          items={items}
        />
      </Layout.Sider>
      <Layout>
        <Layout.Header>
          <div
            className="menu-collapse-trigger-btn"
            onClick={() => setCollapsed(!collapsed)}
          >
            {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
          </div>
        </Layout.Header>
        <Layout.Content>
          <Outlet />
        </Layout.Content>
      </Layout>
    </Layout>
  );
};
export default BaseLayout;
