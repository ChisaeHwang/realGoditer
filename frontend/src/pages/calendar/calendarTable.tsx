import React from "react";

interface UserTask {
  userName: string;
  startDate: string;
  endDate: string | null;
  taskName: string;
}

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

interface ExtendedUserTask extends UserTask {
  periods: { startDay: number; endDay: number; taskName: string }[];
}

interface CalendarTableProps {
  users: ExtendedUserTask[];
  month: number; // 월을 나타내는 속성을 추가합니다.
}

function UserRow({ user, tasks }: { user: ExtendedUserTask; tasks: Task[] }) {
  const getMiddleDay = (startDay: number, endDay: number): number => {
    return startDay + Math.floor((endDay - startDay) / 2);
  };

  return (
    <div className="row">
      <div className="cell user-name">{user.userName}</div>
      {[...Array(36)].map((_, i) => {
        const day = i + 1;
        const period = user.periods.find(
          (p) => day >= p.startDay && day <= p.endDay
        );

        const showTaskName =
          period && day === getMiddleDay(period.startDay, period.endDay);
        const isEvenDuration =
          period && (period.endDay - period.startDay + 1) % 2 === 0;
        // 작업 상태 확인
        const isTaskIncomplete =
          period &&
          tasks.find(
            (t) => t.name === period.taskName && t.status === "COMPLETED"
          );    

        return (
          <div
            className={`cell ${period ? "working" : ""} ${
              isTaskIncomplete ? "incomplete-task" : ""
            }`}
            key={i}
          >
            {showTaskName && (
              <span className={`taskName ${isEvenDuration ? "even" : "odd"}`}>
                {period.taskName}
              </span>
            )}
          </div>
        );
      })}
    </div>
  );
}

export function CalendarTable({
  users,
  month,
  tasks,
}: CalendarTableProps & { tasks: Task[] }) {
  

  return (
    <div className="cell-table">
      <div className="header">
        <div className="cell job-schedule">{month}월 작업 일정</div>{" "}
      </div>
      <div className="header">
        <div className="cell">작업자 명</div>
        {Array.from({ length: 31 }, (_, i) => (
          <div className="cell">{i + 1}</div>
        ))}
        <div className="cell">1</div>
        <div className="cell">2</div>
        <div className="cell">3</div>
        <div className="cell">4</div>
      </div>
      {users.map((user) => (
        <UserRow key={user.userName} user={user} tasks={tasks} />
      ))}
    </div>
  );
}