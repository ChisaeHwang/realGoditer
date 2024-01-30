import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate를 임포트합니다.
import axios from "axios";
import DetailCalculate from "./detailCalculate";
import "./css/calculate.css";

type CalculateResponse = {
  userName: string;
  totalVideoLength: number;
  payPerMinute: number;
  totalIncentive: number;
  monthlySalary: number;
  deductedAmount: number;
  netMonthlySalary: number;
};

const dummyData: CalculateResponse[] = [
  {
    userName: "김영희",
    totalVideoLength: 450,
    payPerMinute: 5000,
    totalIncentive: 50000,
    monthlySalary: 2250000,
    deductedAmount: 200000,
    netMonthlySalary: 2050000,
  },
  {
    userName: "이철수",
    totalVideoLength: 320,
    payPerMinute: 4800,
    totalIncentive: 45000,
    monthlySalary: 1536000,
    deductedAmount: 150000,
    netMonthlySalary: 1386000,
  },
  {
    userName: "박지민",
    totalVideoLength: 500,
    payPerMinute: 5200,
    totalIncentive: 60000,
    monthlySalary: 2600000,
    deductedAmount: 220000,
    netMonthlySalary: 2380000,
  },
];

const Calculate = () => {
  const [data, setData] = useState<CalculateResponse[]>(dummyData);
  const [role, setRole] = useState<string>(""); // 포지션
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear()); // 기본값으로 현재 년도 설정
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1); // 기본값으로 현재 월 설정
  const [totalAmount, setTotalAmount] = useState(0); // 총 금액을 저장하기 위한 state 추가
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [selectedUserName, setSelectedUserName] = useState("");
  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.
  const [isRoleChecked, setIsRoleChecked] = useState(false); // role이 확인되었는지 추적하는 상태

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
          setRole(userRole || "");
          setIsRoleChecked(true); // role 확인 완료

          if (userRole !== "ADMIN") {
            navigate("/main");
            setTimeout(() => {
              alert("정산 기능은 관리자만 접근가능합니다.");
            }, 500); // 0.5초 후에 alert 표시
          }
        })
        .catch((error) => {
          console.error("Failed to fetch user data:", error);
        });
    }

    axios
      .post(
        "https://goditerapi.xyz/api/task/calculate",
        { month: selectedMonth, year: selectedYear },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        // 응답 코드가 숫자 형태인지 확인
        if (response.status === 200) {
          setData(response.data.data);
          console.log(response.data.data);

          // 데이터를 설정한 후 총액을 계산
          // CalculateResponse 타입을 사용하여 curr의 타입을 명시합니다.
          const total = response.data.data.reduce(
            (acc: number, curr: CalculateResponse) =>
              acc + curr.netMonthlySalary,
            0
          );

          setTotalAmount(total);
        } else {
          console.error("API returned an error:", response.data.message);
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 403) {
          alert("접근 권한이 없습니다.");
          navigate("/main");
        } else {
          // navigate("/");
          console.error("Error fetching data:", error);
        }
      });
    // data를 의존성 배열에서 제거하여 무한 루프 방지
  }, [navigate, role, selectedYear, selectedMonth]);

  // 시간 형식으로 변환하는 함수
  const formatTime = (seconds: number) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}분 ${remainingSeconds}초`;
  };

  // 행 클릭 핸들러
  const handleRowClick = (userName: string) => {
    setSelectedUserName(userName);
    setShowDetailModal(true);
  };

  // role이 확인되었고 ADMIN이 아닌 경우에만 내용 렌더링
  if (!isRoleChecked || role !== "ADMIN") {
    return null;
  }

  return (
    <>
      <div className="calculate-container">
        <h2 className="cal-management-header">정산하기</h2>

        <div className="date-selector">
          <select
            value={selectedYear}
            onChange={(e) => setSelectedYear(Number(e.target.value))}
          >
            <option value={2022}>2022년</option>
            <option value={2023}>2023년</option>
            <option value={2024}>2024년</option>
            {/* 필요에 따라 더 많은 년도를 추가할 수 있습니다 */}
          </select>

          <select
            value={selectedMonth}
            onChange={(e) => setSelectedMonth(Number(e.target.value))}
          >
            <option value={1}>1 월</option>
            <option value={2}>2월</option>
            <option value={3}>3월</option>
            <option value={4}>4월</option>
            <option value={5}>5월</option>
            <option value={6}>6월</option>
            <option value={7}>7월</option>
            <option value={8}>8월</option>
            <option value={9}>9월</option>
            <option value={10}>10월</option>
            <option value={11}>11월</option>
            <option value={12}>12월</option>
          </select>
        </div>

        <table className="calculate-table">
          <thead>
            <tr>
              <th>작업자명</th>
              <th>한달 영상 길이</th>
              <th>분당 페이</th>
              <th>인센티브</th>
              <th>이번달 급여</th>
              <th>이번달 세금</th>
              <th>이번달 실급여</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item, index) => (
              <tr key={index} onClick={() => handleRowClick(item.userName)}>
                <td>{item.userName}</td>
                <td>{formatTime(item.totalVideoLength)}</td>{" "}
                {/* 여기에서 변환 함수를 사용합니다 */}
                <td>{item.payPerMinute}</td>
                <td>{item.totalIncentive}</td>
                <td>{item.monthlySalary}</td>
                <td>{item.deductedAmount}</td>
                <td>{item.netMonthlySalary}</td>
              </tr>
            ))}
          </tbody>

          <div className="total-amount">
            {" "}
            <span className="total-label">총 금액</span>
            {totalAmount.toLocaleString()} 원
          </div>
        </table>
      </div>

      {/* Detail 모달 */}
      {showDetailModal && (
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
              padding: "20px", // 내용에 대한 패딩 추가
            }}
          >
            {/* DetailCalculate 컴포넌트를 사용하여 선택된 사용자의 정보를 전달합니다 */}
            <DetailCalculate
              userName={selectedUserName}
              year={selectedYear}
              month={selectedMonth}
              onClose={() => setShowDetailModal(false)}
            />
          </div>
        </div>
      )}
    </>
  );
};

export default Calculate;
