import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import "./css/header.css";
import Information from "../pages/information/Information";

const ArrowIcon = () => (
  <svg
    width="6"
    height="12"
    viewBox="0 0 4 8"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path d="M1 7L3 4L1 1" stroke="#48A9EF" stroke-linecap="round" />
  </svg>
);

const HeaderBar: React.FC = () => {
  const [showInfoModal, setShowInfoModal] = useState(false);
  const [name, setName] = useState("");
  const [role, setRole] = useState<string>(""); // 포지션
  const location = useLocation();
  const [isMenuVisible, setMenuVisible] = useState(false);
  const menuRef = useRef<HTMLDivElement | null>(null);

  // URL에 따른 페이지 제목을 결정하는 함수
  const getPageTitle = (pathname: string) => {
    switch (pathname) {
      case "/main":
        return "HOME";
      case "/calculate":
        return "정산";
      default:
        return "PAGE"; // 기본값, 혹은 다른 페이지 경로에 대한 제목
    }
  };

  // useEffect를 사용하여 컴포넌트가 마운트될 때 사용자 정보를 가져옵니다.
  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await axios.get("https://goditerapi.xyz/api/user", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          const userName = response.data.data.name;
          const userRole = response.data.data.role;
          setName(userName);
          setRole(userRole);
        } catch (error) {
          console.error("Failed to fetch user data:", error);
        }
      }
    };

    fetchUserData();

    // 외부 클릭 감지를 위한 이벤트 리스너
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setMenuVisible(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []); // 빈 의존성 배열은 이 effect가 컴포넌트가 마운트될 때 한 번만 실행되어야 함을 의미합니다.

  const handleOpenInfoeModal = () => {
    setShowInfoModal(true);
  };

  const handleLogout = () => {
    localStorage.removeItem("token"); // 토큰 제거
    window.location.href = "https://goditer-front.vercel.app/"; // localhost:3000으로 리다이렉트
  };

  return (
    <>
      <div className="header-bar">
        <div className="left-section">
          <div className="worker-info">
            <h1 className="worker-name">{name}님</h1>
            <p
              className={`worker-position ${
                role === "THUMBNAILER" ? "worker-position-thumbnailer" : ""
              }`}
            >
              {role === "EDITOR"
                ? "편집자"
                : role === "THUMBNAILER"
                ? "썸네일러"
                : role}{" "}
              <ArrowIcon />
            </p>
          </div>
          <p className="current-page">
            GO.DITER &gt; {getPageTitle(location.pathname)}
          </p>
        </div>
        <div className="profile-section">
          <img
            src="https://via.placeholder.com/150x150?text=Raccoon" // "Raccoon" 텍스트로 표시된 이미지
            alt="프로필 사진"
            className="profile-image"
          />
          <button onClick={() => setMenuVisible(!isMenuVisible)}>{name}</button>
          {isMenuVisible && (
            <div className="profile-menu" ref={menuRef}>
              <button onClick={() => handleOpenInfoeModal()}>
                개인정보 수정
              </button>
              <button onClick={handleLogout}>로그아웃</button>
            </div>
          )}
        </div>
      </div>

      {/* 완료 모달 */}
      {showInfoModal && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            onClick={(e) => e.stopPropagation()}
            style={{
              borderRadius: "45px",
              boxShadow: "0px 0px 30px 0px #0000001A;",
            }}
          >
            <Information onClose={() => setShowInfoModal(false)} />
          </div>
        </div>
      )}
    </>
  );
};

export default HeaderBar;
