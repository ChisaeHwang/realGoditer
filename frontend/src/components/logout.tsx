import { ReactElement } from "react";
import { CustomBtn } from "../commons/btn";
import axios from "axios";

const Logout = (): ReactElement => {
  const handleLogout = async () => {
    const authToken = localStorage.getItem("token");
    if (!authToken) {
      alert("이미 로그아웃 상태입니다.");
      return;
    }

    try {
      // 여기서는 예시로 사용자의 이메일을 가져오는 것을 보여드리기 위해 axios를 사용했습니다.
      // 실제로 이런 방식으로 구현되어 있는지 확인이 필요합니다.
      const response = await axios.get(
        "https://goditerapi.xyz/api/getUserEmail",
        {
          headers: {
            Authorization: `Bearer ${authToken}`,
          },
        }
      );

      const userEmail = response.data;

      // JWT 토큰을 localStorage에서 삭제합니다.
      localStorage.removeItem("token");

      alert(`로그아웃 되었습니다. 토큰: ${authToken}, 이메일: ${userEmail}`);
      window.location.assign("https://goditer-front.vercel.app/");
    } catch (error) {
      console.error("Error during logout:", error);
      alert("로그아웃에 실패했습니다.");
    }
  };

  return <CustomBtn onClick={handleLogout}>Logout</CustomBtn>;
};

export default Logout;
