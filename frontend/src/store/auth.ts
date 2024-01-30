import create from "zustand";
import { devtools } from "zustand/middleware";
interface LoginState {
  isLogin: boolean;
  authToken: string;
  login: Function;
  logout: Function;
}
// set 함수를 통해서만 상태를 변경할 수 있다
const useAuthStore = create(
  devtools<LoginState>((set) => ({
    isLogin: false,
    authToken: "",
    login: (token: string) =>
      set((state) => ({ isLogin: true, authToken: token })),
    logout: () => set((state) => ({ isLogin: false, authToken: undefined })),
  }))
);

export default useAuthStore;
