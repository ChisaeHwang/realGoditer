import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/createTask.css";
import DeleteIcon from "../icon/DeleteIcon";
import PencilIcon from "../icon/PencilIcon";

interface CreateTaskProps {
  onClose: () => void;
}

function CreateTask({ onClose }: CreateTaskProps) {
  const [name, setName] = useState("");
  const [position, setPosition] = useState<string>(""); // 포지션 상태
  const [incentiveAmount, setIncentiveAmount] = useState<string>(""); // 초기값을 빈 문자열로 변경

  // useEffect를 사용하여 컴포넌트가 마운트될 때 사용자 정보를 가져옵니다.
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      axios
        .get("https://goditerapi.xyz/api/user", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          const userRole = response.data.data.role;
          setPosition(userRole || "");
        })
        .catch((error) => {
          console.error("Failed to fetch user data:", error);
        });
    }
  }, []); // 빈 의존성 배열은 이 effect가 컴포넌트가 마운트될 때 한 번만 실행되어야 함을 의미합니다.

  const handleSubmit = () => {
    const token = localStorage.getItem("token");
    if (token && name) {
      axios
        .post(
          "https://goditerapi.xyz/api/task/add",
          {
            name: name,
            incentiveAmount: incentiveAmount,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then((response) => {
          // 작업 생성 후 메인 페이지로 이동
          window.location.href = "/main";
        })
        .catch((error) => {
          console.error("Task creation failed:", error);
        });
    }
  };

  return (
    <div className="create-task-container">
      <button onClick={onClose} className="close-button">
        <DeleteIcon />
      </button>
      <h2 className="create-task-title">
        <PencilIcon />
        작업 생성
      </h2>

      <div className="input-group">
        <label className="input-label">작업명</label>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="input-field"
        />
      </div>

      <div className="role-label">
        <label>포지션</label>
      </div>
      <div className="role-selection">
        <button
          type="button"
          className={`role-button EDITOR ${
            position === "EDITOR" ? "selected" : ""
          }`}
          onClick={() => setPosition("EDITOR")}
        >
          편집자
        </button>
        <button
          type="button"
          className={`role-button THUMBNAILER ${
            position === "THUMBNAILER" ? "selected" : ""
          }`}
          onClick={() => setPosition("THUMBNAILER")}
        >
          썸네일러 / 기타
        </button>
      </div>

      <div className="input-group">
        <div className="input-with-won-wrapper">
          <label className="input-label">페이(급여):</label>
          <input
            type="number"
            value={incentiveAmount}
            onChange={(e) => setIncentiveAmount(e.target.value || "")}
            className="input-field input-with-won"
            disabled={position === "EDITOR"} // 편집자를 선택했을 때 비활성화
          />
          <span className="create-won-text">원</span>
        </div>
      </div>

      <button onClick={handleSubmit} className="submit-button">
        작업 생성
      </button>
    </div>
  );
}

export default CreateTask;
