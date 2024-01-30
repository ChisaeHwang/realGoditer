import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/detail.css";

type DetailInfo = {
  taskName: string;
  videoLength: number;
  startDate: string;
  endDate: string;
  pay: number;
  remarks: string;
};

type DetailCalculateProps = {
  userName: string; // 유저 이름을 prop으로 받습니다.
  year: number;
  month: number;
  onClose: () => void;
};

const DetailCalculate = ({
  userName,
  year,
  month,
  onClose,
}: DetailCalculateProps) => {
  const [details, setDetails] = useState<DetailInfo[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.post(
          "https://goditerapi.xyz/api/task/calculate/detail",
          {
            userName: userName,
            year: year,
            month: month,
          },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );

        if (response.status === 200) {
          console.log(response.data.data);

          setDetails(response.data.data);
        } else {
          console.error("API returned an error:", response.data.message);
        }
      } catch (error) {
        console.error("Error fetching details:", error);
      }
    };

    fetchData();
  }, [userName, year, month]);

  // 시간을 분과 초로 변환하는 함수
  const formatTime = (seconds: number) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}분 ${remainingSeconds}초`;
  };

  return (
    <div className="detail-calculate-container">
      <button onClick={onClose} className="close-button">
        <svg
          width="32"
          height="32"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <rect
            x="0.375"
            y="0.375"
            width="23.25"
            height="23.25"
            rx="10.875"
            fill="white"
            stroke="#808080"
            stroke-width="0.75"
          />
          <path
            d="M15.182 16.1673L12 12.9853L8.81802 16.1673L7.75736 15.1066L10.9393 11.9246L7.75736 8.74264L8.81802 7.68198L12 10.864L15.182 7.68198L16.2426 8.74264L13.0607 11.9246L16.2426 15.1066L15.182 16.1673Z"
            fill="#808080"
          />
        </svg>
      </button>
      <h3 className="detail-header">세부 정산 정보: {userName}</h3>
      <table className="detail-calculate-table">
        <thead>
          <tr>
            <th>작업 이름</th>
            <th>시작 일자</th>
            <th>완료 일자</th>
            <th>영상 길이</th>
            <th>페이</th>
            <th>비고</th>
          </tr>
        </thead>
        <tbody>
          {details.map((detail, index) => (
            <tr key={index}>
              <td>{detail.taskName}</td>
              <td>{detail.startDate}</td>
              <td>{detail.endDate}</td>
              <td>{formatTime(detail.videoLength)}</td>{" "}
              {/* 여기에서 변환 함수를 사용합니다 */}
              <td>{detail.pay.toLocaleString()} 원</td>
              <td>{detail.remarks}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DetailCalculate;
