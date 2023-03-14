import { cssMainFooterStyle, cssPlusPostBtnStyle } from './MainFooter.styles';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Layout } from 'antd';
import { HomeOutlined, SearchOutlined, UserOutlined } from '@ant-design/icons';
import { ReactComponent as ModifyFontSize } from '../../assets/images/icons/modify-font-size.svg';
import { ReactComponent as PlusPost } from '../../assets/images/icons/plus-post.svg';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';

const MainFooter = () => {
  const navigate = useNavigate();

  const items: MenuProps['items'] = [
    {
      key: '/post/help-request',
      label: <Link to="/post/help-request">도움요청</Link>,
    },
    {
      key: '/post/help-serve',
      label: <Link to="/post/help-serve">도움제공</Link>,
    },
    {
      key: '/post/free',
      label: <Link to="/post/free">자유</Link>,
    },
  ];

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <Layout.Footer css={cssMainFooterStyle}>
      <Button>
        <ModifyFontSize />
      </Button>
      <Button onClick={() => handleOnClickLinkBtn(PATH.HOME)}>
        <NavLink
          to={PATH.HOME}
          className={({ isActive }) => (isActive ? 'active' : undefined)}
        >
          <HomeOutlined />
        </NavLink>
      </Button>

      <Dropdown menu={{ items }} placement="top" arrow trigger={['click']}>
        <Button css={cssPlusPostBtnStyle}>
          <PlusPost />
        </Button>
      </Dropdown>

      <Button onClick={() => handleOnClickLinkBtn(PATH.SEARCH)}>
        <NavLink
          to={PATH.SEARCH}
          className={({ isActive }) => (isActive ? 'active' : undefined)}
        >
          <SearchOutlined />
        </NavLink>
      </Button>
      <Button onClick={() => handleOnClickLinkBtn(PATH.MY_PAGE)}>
        <NavLink
          to={PATH.MY_PAGE}
          className={({ isActive }) => (isActive ? 'active' : undefined)}
        >
          <UserOutlined />
        </NavLink>
      </Button>
    </Layout.Footer>
  );
};

export default MainFooter;
