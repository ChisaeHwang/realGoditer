import { ReactElement } from "react";
// import Google from "../components/google";
import "./css/Home.css";
import { Wave1, Wave2, Wave3 } from "../components/wave";

const Home = (): ReactElement => {
  const redirectToGoogleOAuth = (): void => {
    window.location.assign(
      "https://goditerapi.xyz/oauth2/authorization/google"
    );
  };

  return (
    <div className="home-container">
      <div className="content-container">
        <div className="title">GO.DITER</div>
        <div className="sub-title">스트리머를 위한 정산 프로그램</div>
        <button className="google-login-btn" onClick={redirectToGoogleOAuth}>
          <img
            src="/google-logo.svg"
            alt="Google Logo"
            className="google-logo"
          />
          <span className="button-text">Login With Google</span>
        </button>
        <div className="info-text">구글 로그인로 회원가입 하기</div>
      </div>
      <div className="waves">
        <Wave3 />
        <Wave2 />
        <Wave1 />
      </div>
    </div>
  );
};

export default Home;
