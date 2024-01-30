import React from "react";
import { NavLink } from "react-router-dom";
import "./css/channel.css";

const Icon: React.FC = () => (
  <svg
    width="15"
    height="15"
    viewBox="0 0 15 15"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      d="M7.5 1.25L1 5.75V13.75H14V5.75L7.5 1.25Z"
      stroke="#CCCCCC"
      strokeWidth="2"
    />
  </svg>
);

const ChannelBar: React.FC = () => {
  // NavLinkProps의 isActive 속성에 대한 타입 정의
  const setActiveClass = ({ isActive }: { isActive: boolean }) =>
    isActive ? "active" : "";

  return (
    <div className="channel-bar">
      <div className="channel-title">GO.DITER</div>
      <ul>
        <li>
          <NavLink to="/main" className={setActiveClass}>
            <Icon />
            MY 홈
          </NavLink>
        </li>
        <li>
          <NavLink to="/calculate" className={setActiveClass}>
            <svg
              className="icon-calculate" // 이 클래스 이름을 추가합니다.
              width="20"
              height="12"
              viewBox="0 0 20 12"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <rect
                x="1"
                y="1"
                width="18"
                height="10"
                stroke="#CCCCCC"
                stroke-width="2"
              />
              <circle cx="10" cy="6" r="2" stroke="#CCCCCC" stroke-width="2" />
            </svg>
            정산하기
          </NavLink>
        </li>
      </ul>
    </div>
  );
};

export default ChannelBar;
