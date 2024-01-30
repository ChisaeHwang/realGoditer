import React from "react";
import "./css/LoadingScreen.css";

const Dot = () => (
  <div className="loading-dot"></div>
);

const LoadingScreen: React.FC = () => {
  return (
    <div className="loading-container">
      <div className="loading-dots-container">
        <Dot />
        <Dot />
        <Dot />
      </div>
    </div>
  );
};

export default LoadingScreen;
