import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate를 임포트합니다.
import HeaderBar from "../components/header";
import ChannelBar from "../components/channel";
import Calculate from "./calculate/calculate";
import LoadingScreen from "../components/LoadingScreen";
import "./css/mainspace.css";

function CalculateSpace() {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/");
    }

    // 데이터 로딩 시뮬레이션
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700); // 0.5초 후에 로딩 상태를 false로 변경합니다.

    // 컴포넌트 언마운트시 타이머 클리어
    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="main-container">
      <div className="main-sidebar">
        <ChannelBar />
      </div>
      {loading ? (
        <LoadingScreen /> // 로딩 애니메이션 컴포넌트를 렌더링합니다.
      ) : (
        <div className="main-content">
          <HeaderBar />
          <Calculate />
        </div>
      )}
    </div>
  );
}

export default CalculateSpace;
