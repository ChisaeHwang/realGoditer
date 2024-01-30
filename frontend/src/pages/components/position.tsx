import React, { useState, useEffect } from "react";
import axios from "axios";
import DeleteIcon from "../icon/DeleteIcon";
import EditIcon from "../icon/EditIcon";
import CompletedIcon from "../icon/CompletedIcon";
import UpdateTask from "../task/updateTask";
import CompleteTask from "../task/completeTask";

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

interface TaskList {
  id: number;
  year: number;
  month: number;
  tasks: Task[];
}

interface PositionProps {
  tasks: Task[]; // Task 배열을 받는다고 명시
}

function Position({ tasks }: PositionProps) {
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showCompleteModal, setShowCompleteModal] = useState(false); // 추가된 상태
  const [selectedTaskId, setSelectedTaskId] = useState<number | null>(null);
  const [taskLists, setTaskLists] = useState<TaskList[]>([]);
  const [name, setName] = useState("");

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
          setName(response.data.data.name);
        })
        .catch((error) => {
          console.error("Failed to fetch user data:", error);
        });
    }
  }, []); // 빈 의존성 배열은 이 effect가 컴포넌트가 마운트될 때 한 번만 실행되어야 함을 의미합니다.

  const handleDeleteTask = (taskId: number, taskCreator: string) => {
    // 본인의 작업이 아니면 알림을 표시하고 삭제를 중단합니다.
    if (name !== taskCreator) {
      alert("본인의 작업만 삭제할 수 있습니다.");
      return;
    }

    // 사용자에게 삭제 여부를 물어봅니다.
    if (window.confirm("이 작업을 삭제하시겠습니까?")) {
      axios
        .delete(`https://goditerapi.xyz/api/task/delete/${taskId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        })
        .then(() => {
          // 성공적으로 삭제한 후의 로직 (예: 삭제한 작업을 UI에서 제거)
          setTaskLists(
            taskLists.filter((taskList) => {
              taskList.tasks = taskList.tasks.filter(
                (task) => task.id !== taskId
              );
              return taskList.tasks.length > 0;
            })
          );
          window.location.assign("https://goditer-front.vercel.app/main");
          // 페이지 리로드 대신 상태를 업데이트합니다.
        })
        .catch((error) => {
          console.error("Task deletion failed:", error);
        });
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    const month = date.getMonth() + 1; // getMonth는 0부터 시작하기 때문에 1을 더해줌
    const day = date.getDate();
    return `${month} / ${day}`;
  };

  // 진행일 수를 계산하는 함수
  const calculateProgressDays = (
    startDateString: string,
    endDateString: string
  ) => {
    const startDate = new Date(startDateString);
    let endDate = new Date(endDateString); // 마감 날짜

    // 시간을 0으로 설정하여 전체 일자만 비교
    startDate.setHours(0, 0, 0, 0);
    endDate.setHours(0, 0, 0, 0);

    // 시작 날짜와 마감 날짜가 동일하면 현재 날짜를 사용
    if (startDate.getTime() === endDate.getTime()) {
      endDate = new Date(); // 현재 날짜로 설정
      endDate.setHours(0, 0, 0, 0); // 시간을 0으로 설정
    }

    const timeDiff = endDate.getTime() - startDate.getTime();
    const daysDiff = Math.floor(timeDiff / (1000 * 3600 * 24)) + 1; // 밀리초를 일로 변환

    return daysDiff >= 0 ? daysDiff : 0; // 음수가 나오지 않도록 처리
  };

  const handleOpenUpdateModal = (taskId: number, taskCreator: string) => {
    if (name !== taskCreator) {
      // 현재 로그인한 사용자의 이름과 태스크 생성자의 이름이 다를 경우
      alert("다른 사용자의 작업은 수정할 수 없습니다."); // 알림 창 표시
    } else {
      // 같을 경우 모달을 열기
      setSelectedTaskId(taskId);
      setShowUpdateModal(true);
    }
  };

  const handleOpenCompleteModal = (taskId: number, taskCreator: string) => {
    if (name !== taskCreator) {
      alert("다른 사용자의 작업은 완료 처리할 수 없습니다.");
    } else {
      setSelectedTaskId(taskId);
      setShowCompleteModal(true);
    }
  };

  const getRoleDetails = (role: string) => {
    switch (role) {
      case "EDITOR":
        return { text: "편집자", color: "#48a9ef", backgroundColor: "#E0F7FA" }; // 예시 배경색 추가
      case "THUMBNAILER":
        return { text: "디자인", color: "#EF8E48", backgroundColor: "#FBE9E7" }; // 예시 배경색 추가
      default:
        return { text: "", color: "", backgroundColor: "" };
    }
  };

  return (
    <>
      {tasks.map((task) => (
        <div
          className={`task-box ${
            task.role === "EDITOR"
              ? task.status === "COMPLETED"
                ? "COMPLETED"
                : "EDITOR"
              : "THUMBNAILER"
          }`}
          key={task.id}
        >
          {" "}
          {/* index 대신 task.id 사용 */}
          <p className="video-name">{task.name}</p>
          <div className="worker-details">
            <div>
              <span className="worker-name">{task.creator}</span>
              <span
                className="position"
                style={{
                  color: getRoleDetails(task.role).color,
                  backgroundColor: getRoleDetails(task.role).backgroundColor,
                }} // 색상을 인라인 스타일로 적용
              >
                {getRoleDetails(task.role).text}
              </span>
              {/* position 정보가 없으므로 아래 코드는 주석 처리 혹은 삭제 */}
              {/* <span className="position">편집자</span> */}
            </div>
          </div>
          <div className="time-details">
            <p className="start-time">시작 시간 {formatDate(task.startDate)}</p>
            <p className="progress-day">
              진행일 {calculateProgressDays(task.startDate, task.endDate)}일
            </p>
          </div>
          <button
            onClick={() => handleDeleteTask(task.id, task.creator)}
            className="delete-task"
          >
            <DeleteIcon />
          </button>
          <div className="task-actions">
            <button
              onClick={() => handleOpenUpdateModal(task.id, task.creator)}
            >
              <EditIcon />
            </button>
            {task.status !== "COMPLETED" && (
              <button
                onClick={() => handleOpenCompleteModal(task.id, task.creator)}
              >
                <CompletedIcon />
              </button>
            )}
          </div>
        </div>
      ))}

      {/* 수정 모달 */}
      {showUpdateModal && selectedTaskId && (
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
            onClick={(e) => e.stopPropagation()} // 이 클릭 핸들러는 모달의 외부 클릭 핸들러를 방지합니다.
            style={{
              borderRadius: "45px",
              boxShadow: "0px 0px 30px 0px #0000001A;",
            }}
          >
            <UpdateTask
              taskId={selectedTaskId!}
              onClose={() => setShowUpdateModal(false)}
            />
          </div>
        </div>
      )}

      {/* 완료 모달 */}
      {showCompleteModal && selectedTaskId && (
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
            <CompleteTask
              taskId={selectedTaskId!}
              onClose={() => setShowCompleteModal(false)}
            />
          </div>
        </div>
      )}
    </>
  );
}

export default Position;
