import { atom } from 'recoil';

export const headerTitleState = atom<string | null>({
  key: 'header-title',
  default: null,
});
