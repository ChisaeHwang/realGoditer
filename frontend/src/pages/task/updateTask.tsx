import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/UpdateTask.css";
import DeleteIcon from "../icon/DeleteIcon";
import PencilIcon from "../icon/PencilIcon";

interface UpdateTaskProps {
  taskId: number;
  onClose: () => void; // 이 줄을 추가합니다.
}

type TaskStatus = "IN_PROGRESS" | "COMPLETED";

function UpdateTask({ taskId, onClose }: UpdateTaskProps) {
  const [videoLengthMinutes, setVideoLengthMinutes] = useState<string>("");
  const [videoLengthSeconds, setVideoLengthSeconds] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [role, setRole] = useState<string>(""); // 포지션
  const [creator, setCreator] = useState<string>(""); // 작업자 이름
  const [pay, setPay] = useState<string>(""); // 페이
  const [incentiveAmount, setIncentiveAmount] = useState<string>(""); // 인센티브
  const [status, setStatus] = useState<TaskStatus>("IN_PROGRESS"); // 작업상황
  const [startDate, setStartDate] = useState<string>(""); // 작업시작일자
  const [endDate, setEndDate] = useState<string>(""); // 작업마감일자
  const [remarks, setRemarks] = useState<string>("");

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

        // 속성 확인 및 설정
        // 속성 확인 및 설정
        if (
          data &&
          data.name &&
          typeof data.videoLengthMinutes === "number" &&
          typeof data.videoLengthSeconds === "number" &&
          typeof data.incentiveAmount === "number" &&
          data.remark !== undefined && // remark를 정확히 체크합니다.
          data.role &&
          typeof data.pay === "number" && // 'number' 문자열이 아니라 typeof를 사용합니다.
          data.startDate &&
          data.endDate &&
          data.creator &&
          data.status
        ) {
          setName(data.name);
          setVideoLengthMinutes(Math.floor(data.videoLengthMinutes).toString()); // 소수점 이하를 버립니다.
          setVideoLengthSeconds(data.videoLengthSeconds.toString());
          setIncentiveAmount(data.incentiveAmount.toString());
          setPay(data.pay);
          setRemarks(data.remark); // 'remarks' 대신 'remark'를 사용합니다.
          setRole(data.role);
          setStartDate(data.startDate);
          setEndDate(data.endDate);
          setCreator(data.creator);
          setStatus(data.status);
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
      startDate,
      endDate,
      status,
      remarks,
      creator,
    };

    axios
      .post(
        `https://goditerapi.xyz/api/task/update/${taskId}`,
        payload, // 수정된 페이로드 사용
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        // 성공적으로 작업이 수정된 후, /main으로 리다이렉트
        window.location.href = "/main";
      })
      .catch((error) => {
        console.error("Task update failed:", error);
      });
  };

  return (
    <div className="update-task-container">
      <button onClick={onClose} className="close-button">
        <DeleteIcon />
      </button>
      <h2 className="update-task-title">
        <PencilIcon />
        작업 수정
      </h2>

      <div className="scrollable-content">
        <div className="input-group">
          <label className="input-label">작업자명</label>
          <input
            type="text"
            value={creator}
            onChange={(e) => setCreator(e.target.value)}
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
          <label className="input-label">작업명</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="input-field"
          />
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

        <div className="input-group">
          <label className="input-label">작업 상태</label>
          <div className="status-group">
            <select
              value={status}
              onChange={(e) => setStatus(e.target.value as TaskStatus)}
              className="status-select"
            >
              <option value="IN_PROGRESS">진행 중</option>
              <option value="COMPLETED">완료됨</option>
            </select>
          </div>
        </div>

        <div className="input-group">
          <label className="input-label">비고란</label>
          <input
            value={remarks}
            onChange={(e) => setRemarks(e.target.value)}
            className="input-field remarks-textarea"
            placeholder="비고를 여기에 입력하세요..."
          />
        </div>
      </div>

      <button onClick={handleSubmit} className="submit-button">
        작업 수정
      </button>
    </div>
  );
}

export default UpdateTask;
