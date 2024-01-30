import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/CompleteTask.css";
import DeleteIcon from "../icon/DeleteIcon";
import PencilIcon from "../icon/PencilIcon";

interface CompleteTaskProps {
  taskId: number;
  onClose: () => void;
}

function CompleteTask({ taskId, onClose }: CompleteTaskProps) {
  const [name, setName] = useState<string>("");
  const [remark, setRemark] = useState<string>(""); // 비고란
  const [role, setRole] = useState<string>(""); // 포지션
  const [pay, setPay] = useState<string>(""); // 평균 임금
  const [incentiveAmount, setIncentiveAmount] = useState<string>(""); // 인센티브
  const [videoLengthMinutes, setVideoLengthMinutes] = useState<string>("");
  const [videoLengthSeconds, setVideoLengthSeconds] = useState<string>("");

  useEffect(() => {
    axios
      .get(`https://goditerapi.xyz/api/task/${taskId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        const { data, code } = response.data;

        // 응답 코드가 200이 아니면 에러 처리
        if (code !== "200") {
          console.error("API response error:", response.data.message);
          return;
        }

        if (
          data &&
          data.name &&
          typeof data.videoLengthMinutes === "number" &&
          typeof data.videoLengthSeconds === "number" &&
          typeof data.incentiveAmount === "number" &&
          data.remark !== undefined && // remark를 정확히 체크합니다.
          data.role &&
          typeof data.pay === "number"
        ) {
          setName(data.name);
          setVideoLengthMinutes(Math.floor(data.videoLengthMinutes).toString()); // 소수점 이하를 버립니다.
          setVideoLengthSeconds(data.videoLengthSeconds.toString());
          setIncentiveAmount(data.incentiveAmount.toString());
          setPay(data.pay);
          setRemark(data.remark); // 'remarks' 대신 'remark'를 사용합니다.
          setRole(data.role);
        } else {
          console.error("Unexpected API response structure:", data);
        }
      })
      .catch((error) => {
        console.error("API request failed:", error);
      });
  }, [taskId]);

  const handleSubmit = () => {
    const totalVideoLengthInSeconds =
      (videoLengthMinutes ? parseInt(videoLengthMinutes, 10) * 60 : 0) +
      (videoLengthSeconds ? parseInt(videoLengthSeconds, 10) : 0);

    const payload = {
      name,
      videoLength: totalVideoLengthInSeconds, // 분과 초를 초로 변환하여 전송
      incentiveAmount: incentiveAmount ? parseFloat(incentiveAmount) : 0,
      role,
      pay: pay ? parseFloat(pay) : 0, // pay도 형변환을 해서 서버에 맞게 보냅니다.
      remark,
    };

    axios
      .post(`https://goditerapi.xyz/api/task/complete/${taskId}`, payload, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        // 성공적으로 작업이 수정된 후, /main으로 리다이렉트
        window.location.href = "/main";
      })
      .catch((error) => {
        console.error("Task update failed:", error);
      });
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
              role === "EDITOR" ? "selected" : ""
            }`}
            onClick={() => setRole("EDITOR")}
          >
            편집자
          </button>
          <button
            type="button"
            className={`role-button THUMBNAILER ${
              role === "THUMBNAILER" ? "selected" : ""
            }`}
            onClick={() => setRole("THUMBNAILER")}
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
              disabled={role === "THUMBNAILER"}
              placeholder="분"
            />
            <input
              type="number"
              value={videoLengthSeconds}
              onChange={(e) => setVideoLengthSeconds(e.target.value)}
              className="input-field time-input"
              disabled={role === "THUMBNAILER"}
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
              disabled={role === "THUMBNAILER"}
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

export default CompleteTask;
