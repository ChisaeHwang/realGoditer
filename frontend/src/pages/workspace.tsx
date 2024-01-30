import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/worksapce.css";
import { useNavigate } from "react-router-dom"; // useNavigate를 임포트합니다.
import ArrowIcon from "./icon/Arrow";
import Position from "./components/position";
import CreateTask from "./task/createTask";
import DirectTask from "./task/directTask";
import Calendar from "./calendar/calendar";

interface Task {
  id: number;
  name: string;
  videoLength: number;
  incentiveAmount: number;
  startDate: string;
  endDate: string;
  creator: string;
  status: string;
  role: string; // 'EDITOR' 또는 'THUMBNAILER'
}

function Workspace() {
  type ViewMode = "role" | "worker";

  const [tasks, setTasks] = useState<Task[]>([]); // API로부터 받아온 작업들의 상태를 저장할 곳
  const [nextTasks, setNextTasks] = useState<Task[]>([]); // API로부터 받아온 작업들의 상태를 저장할 곳
  const [viewMode, setViewMode] = React.useState<ViewMode>("role");
  const [showCreateModal, setShowCreateModal] = React.useState(false);
  const [showDirectCreateModal, setShowDirectCreateModal] =
    React.useState(false);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1);
  const navigate = useNavigate(); // useNavigate 훅을 사용합니다.

  const handleViewChange = (mode: ViewMode) => {
    setViewMode(mode);
  };

  const handleYearChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedYear(parseInt(event.target.value, 10));
  };

  const handleMonthChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedMonth(parseInt(event.target.value, 10));
  };

  const getNextMonthYear = (year: number, month: number) => {
    const nextMonth = month === 12 ? 1 : month + 1;
    const nextYear = month === 12 ? year + 1 : year;
    return { nextMonth, nextYear };
  };


  useEffect(() => {

    const { nextMonth, nextYear } = getNextMonthYear(selectedYear, selectedMonth);   

    axios
      .post(
        "https://goditerapi.xyz/api/task/all",
        { month: selectedMonth, year: selectedYear },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        if (response.data.code === "200") {
          setTasks(response.data.data as Task[]); // as 키워드를 사용하여 타입 단언
        } else {
          console.error("API returned an error:", response.data.message);
        }
      })
      .catch((error) => {
        navigate("/");
        console.error("Error fetching data:", error);
      });

    axios
      .post(
        "https://goditerapi.xyz/api/task/all",
        { month: nextMonth, year: nextYear },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        if (response.data.code === "200") {
          setNextTasks(response.data.data as Task[]); // as 키워드를 사용하여 타입 단언
          
        } else {
          console.error("API returned an error:", response.data.message);
        }
      })
      .catch((error) => {
        navigate("/");
        console.error("Error fetching data:", error);
      });
    
  }, [selectedMonth, selectedYear, navigate]); // 빈 의존성 배열은 컴포넌트가 마운트될 때 한 번만 실행됨을 의미한다.

  // 필터링된 작업들
  const editorTasks = tasks.filter(
    (task) => task.status === "IN_PROGRESS" && task.role === "EDITOR"
  );
  const completedTasks = tasks.filter(
    (task) => task.status === "COMPLETED" && task.role === "EDITOR"
  );
  const otherTasks = tasks.filter((task) => task.role === "THUMBNAILER");

  // 작업자별로 작업 분류
  const groupTasksByWorker = (tasks: any) => {
    return tasks.reduce((acc: any, task: any) => {
      // creator 필드를 사용해 작업자별로 그룹화
      if (!acc[task.creator]) {
        acc[task.creator] = [];
      }
      acc[task.creator].push(task);
      return acc;
    }, {});
  };

  // 작업자별 작업 목록 렌더링
  const renderTasksByWorker = (groupedTasks: any) => {
    return Object.keys(groupedTasks).map((worker) => {
      // 작업자의 첫번째 작업을 사용하여 포지션 결정 (모든 작업이 동일한 포지션을 가정)
      const position = groupedTasks[worker][0]?.role;
      const positionColor = position === "EDITOR" ? "#48a9ef" : "#ef8e48";

      return (
        <div key={worker}>
          <h3 className="editor-task">
            {worker}
            {/* 포지션에 따른 동그라미 표시 */}
            <span
              style={{
                display: "inline-block",
                marginLeft: "8px",
                width: "10px",
                height: "10px",
                borderRadius: "50%",
                backgroundColor: positionColor,
              }}
            ></span>
          </h3>
          <div className="task-visibility" style={{ minHeight: "200px" }}>
            <Position tasks={groupedTasks[worker]} />
          </div>
        </div>
      );
    });
  };

  const groupedTasks = groupTasksByWorker(tasks);

  return (
    <div className="workspace-container">
      <h2 className="management-header">
        관리하기{" "}
        <svg
          className="icon-adjusted" // 이 클래스 이름을 추가합니다.
          width="2"
          height="20"
          viewBox="0 0 1 5"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <line x1="0.5" y1="2.18557e-08" x2="0.5" y2="10" stroke="#CCCCCC" />
        </svg>
        {/* 날짜 선택 드롭다운 */}
        <select value={selectedYear} onChange={handleYearChange}>
          {/* 연도 선택 옵션을 동적으로 생성 */}
          {Array.from({ length: 3 }, (_, i) => (
            <option key={i} value={new Date().getFullYear() - i}>
              {new Date().getFullYear() - i}년
            </option>
          ))}
        </select>
        <select value={selectedMonth} onChange={handleMonthChange}>
          {/* 월 선택 옵션을 동적으로 생성 */}
          {Array.from({ length: 12 }, (_, i) => (
            <option key={i} value={i + 1}>
              {i + 1}월
            </option>
          ))}
        </select>
      </h2>
      <div className="management-buttons">
        <div className="management-buttons">
          <button
            className="create-task-btn"
            onClick={() => setShowCreateModal(true)}
          >
            <span className="btn-text-icon-wrapper">
              <span>작업 생성하기</span>
              <div className="icon">
                <ArrowIcon />
              </div>
            </span>
            <div>
              작업 시작할 때<br />
              폴더를 생성해주세요
            </div>
          </button>

          <button
            className="manage-pay-btn"
            onClick={() => setShowDirectCreateModal(true)}
          >
            <span className="btn-text-icon-wrapper">
              <span>한 번에 만들기</span>
              <div className="icon">
                <ArrowIcon />
              </div>
            </span>
            <div>
              이미 작업한 영상을
              <br />
              한번에 입력하세요
            </div>
          </button>
        </div>
      </div>
      <div className="separator-line"></div>
      <div className="task-visibility-header">
        <button
          className={viewMode === "role" ? "selected" : ""}
          onClick={() => handleViewChange("role")}
        >
          직군별
        </button>{" "}
        <svg
          width="2"
          height="20"
          viewBox="0 0 1 5"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <line x1="0.5" y1="2.18557e-08" x2="0.5" y2="10" stroke="#CCCCCC" />
        </svg>
        <button
          className={viewMode === "worker" ? "selected" : ""}
          onClick={() => handleViewChange("worker")}
        >
          작업자별
        </button>
      </div>

      {/* 직군별 뷰 */}
      {viewMode === "role" && (
        <>
          <h3 className="editor-task">편집 작업 현황</h3>
          <div className="task-visibility" style={{ minHeight: "200px" }}>
            <Position tasks={editorTasks} />
          </div>
          <h3 className="editor-task">편집 완료 현황</h3>
          <div className="task-visibility" style={{ minHeight: "200px" }}>
            <Position tasks={completedTasks} />
          </div>
          <h3 className="editor-task">기타 작업</h3>
          <div className="task-visibility" style={{ minHeight: "200px" }}>
            <Position tasks={otherTasks} />
          </div>
        </>
      )}

      {/* 작업자별 뷰 */}
      {viewMode === "worker" && renderTasksByWorker(groupedTasks)}

      <Calendar tasks={tasks} nextTasks={nextTasks} currentM={selectedMonth} />

      <div className="footer">
        <h1>개발: 치새황 | 디자인: 오슈이</h1>
      </div>

      {showCreateModal && (
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
            <CreateTask onClose={() => setShowCreateModal(false)} />
          </div>
        </div>
      )}

      {showDirectCreateModal && (
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
            <DirectTask onClose={() => setShowDirectCreateModal(false)} />
          </div>
        </div>
      )}
    </div>
  );
}

export default Workspace;
