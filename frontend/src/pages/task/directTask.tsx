import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/createTask.css";
import DeleteIcon from "../icon/DeleteIcon";
import PencilIcon from "../icon/PencilIcon";

interface DirectTaskProps {
  onClose: () => void;
}

function DirectTask({ onClose }: DirectTaskProps) {
  const [name, setName] = useState("");
  const [position, setPosition] = useState<string>(""); // 포지션 상태
  const [incentiveAmount, setIncentiveAmount] = useState<string>(""); // 초기값을 빈 문자열로 변경
  const [remark, setRemark] = useState<string>(""); // 비고란
  const [pay, setPay] = useState<string>(""); // 평균 임금
  const [videoLengthMinutes, setVideoLengthMinutes] = useState<string>("");
  const [videoLengthSeconds, setVideoLengthSeconds] = useState<string>("");
  const [startDate, setStartDate] = useState<string>(""); // 작업시작일자
  const [endDate, setEndDate] = useState<string>(""); // 작업마감일자

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
          const userPay = response.data.data.pay;
          setPosition(userRole || "");
          setPay(userPay);
        })
        .catch((error) => {
          console.error("Failed to fetch user data:", error);
        });
    }
  }, []);

  const getCurrentDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = () => {
    const today = getCurrentDate();
    const finalStartDate = startDate || today;
    const finalEndDate = endDate || today;

    const totalVideoLengthInSeconds =
      (videoLengthMinutes ? parseInt(videoLengthMinutes, 10) * 60 : 0) +
      (videoLengthSeconds ? parseInt(videoLengthSeconds, 10) : 0);

    const token = localStorage.getItem("token");
    if (token && name) {
      axios
        .post(
          "https://goditerapi.xyz/api/task/add/complete",
          {
            name: name,
            videoLength: totalVideoLengthInSeconds, // 분과 초를 초로 변환하여 전송
            incentiveAmount: incentiveAmount,
            position,
            pay: pay ? parseFloat(pay) : 0, // pay도 형변환을 해서 서버에 맞게 보냅니다.
            startDate: finalStartDate,
            endDate: finalEndDate,
            remark,
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
    <div className="complete-task-container">
      <button onClick={onClose} className="close-button">
        <DeleteIcon />
      </button>
      <h2 className="complete-task-title">
        <PencilIcon />
        작업 완료
      </h2>

      <div className="scrollable-content">
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
          <label className="input-label">영상 길이</label>
          <div className="time-input-group">
            <input
              type="number"
              value={videoLengthMinutes}
              onChange={(e) => setVideoLengthMinutes(e.target.value)}
              className="input-field time-input"
              disabled={position === "THUMBNAILER"}
              placeholder="분"
            />
            <input
              type="number"
              value={videoLengthSeconds}
              onChange={(e) => setVideoLengthSeconds(e.target.value)}
              className="input-field time-input"
              disabled={position === "THUMBNAILER"}
              placeholder="초"
            />
          </div>
        </div>

        <div className="input-group pay-group">
          <label className="input-label">페이(분당)</label>
          <div className="input-with-won-wrapper">
            <input
              type="number"
              value={pay}
              onChange={(e) => setPay(e.target.value)}
              className="input-field input-with-won"
              disabled={position === "THUMBNAILER"}
            />
            <span className="won-text">원</span>
          </div>
        </div>

        <div className="input-group incentive-group">
          <label className="input-label">인센티브 금액 or 작업 금액</label>
          <div className="input-with-won-wrapper">
            <input
              type="number"
              value={incentiveAmount}
              onChange={(e) => setIncentiveAmount(e.target.value)}
              className="input-field input-with-won"
            />
            <span className="won-text">원</span>
          </div>
        </div>

        <div className="input-group">
          <label className="input-label">시작 날짜</label>
          <input
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            className="input-field"
          />
        </div>

        <div className="input-group">
          <label className="input-label">종료 날짜</label>
          <input
            type="date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            className="input-field"
          />
        </div>

        <div style={{ marginBottom: "10px" }}>
          <div className="input-group">
            <label className="input-label">비고란</label>
            <input
              value={remark}
              onChange={(e) => setRemark(e.target.value)}
              className="input-field remarks-textarea"
              placeholder="비고를 여기에 입력하세요..."
            />
          </div>
        </div>
      </div>

      <button onClick={handleSubmit} className="submit-button">
        작업 완료
      </button>
    </div>
  );
}

export default DirectTask;
