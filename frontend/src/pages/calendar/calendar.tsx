import React, { useState, useEffect } from "react";
import { CalendarTable } from "./calendarTable";
import axios from "axios";
import "./calendar.css";

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

interface CalendarProps {
  tasks: Task[]; // Task 배열을 받는다고 명시
  nextTasks: Task[];
  currentM: number;
}

interface ExtendedUserTask extends UserTask {
  periods: { startDay: number; endDay: number; taskName: string }[];
}

function Calendar({ tasks, nextTasks, currentM }: CalendarProps) {
  const [userData, setUserData] = useState<ExtendedUserTask[]>([]);
  const currentMonth = currentM;

  useEffect(() => {
    // API 호출을 통해 사용자 이름을 가져옵니다.
    axios
      .get("https://goditerapi.xyz/api/user/all", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        if (response.data.code === "200") {
          const nonAdminUsers = response.data.data.filter(
            (user: { role: string }) => user.role !== "ADMIN"
          );

          // API 응답으로부터 사용자 이름 목록을 가져옵니다.
          const userNamesFromApi = nonAdminUsers.map(
            (user: { name: string }) => user.name
          );
          // 기존 작업에서 사용자 이름을 가져옵니다.
          // 'ADMIN' 역할을 가진 사용자의 작업을 제외하고 사용자 이름을 추출합니다.
          const nonAdminTaskCreators = tasks
            .filter((task) => task.role !== "ADMIN")
            .map((task) => task.creator);

          // 중복을 제거하면서 두 목록을 합칩니다.
          const allUserNames = new Set([
            ...userNamesFromApi,
            ...nonAdminTaskCreators,
          ]);

          // 모든 사용자 이름에 대해 UserTask 객체를 생성합니다.
          const userTasks: UserTask[] = Array.from(allUserNames).map(
            (userName) => ({
              userName,
              startDate: "", // 실제 날짜를 사용하거나, 다른 초기값 설정 가능
              endDate: null,
              taskName: "",
            })
          );

          // 기존 작업을 UserTask 객체로 변환합니다.
          // 'ADMIN' 역할을 가진 사용자의 작업을 제외하고 UserTask 객체로 변환합니다.
          const userTasksFromExistingTasks = tasks
            .filter((task) => nonAdminTaskCreators.includes(task.creator))
            .map((task: Task) => ({
              userName: task.creator,
              startDate: task.startDate,
              endDate: task.endDate || null,
              taskName: task.name,
            }));

          // nextTasks도 organizeData에 포함
          const userTasksFromNextTasks = nextTasks.map((task: Task) => ({
            userName: task.creator,
            startDate: task.startDate,
            endDate: task.endDate || null, // endDate가 없는 경우 처리
            taskName: task.name,
          }));

          // 두 목록을 합쳐서 organizeData 함수를 호출합니다.
          setUserData(
            organizeData(
              [
                ...userTasks,
                ...userTasksFromExistingTasks,
                ...userTasksFromNextTasks,
              ],
              currentM
            )
          );
        } else {
          console.error("API returned an error:", response.data.message);
        }
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });

    // 기존의 로그와 데이터 정리 코드는 그대로 유지됩니다.
  }, [tasks, nextTasks, currentM]);

  const combinedTasks = [...tasks, ...nextTasks];

  const organizeData = (
    data: UserTask[],
    currentMonth: number
  ): ExtendedUserTask[] => {
    const organizedData: { [key: string]: ExtendedUserTask } = {};

    data.forEach((item) => {
      let startDay, endDay;

      if (!item.endDate) {
        item.endDate = new Date().toISOString().split("T")[0];
      }

      const startDate = new Date(item.startDate);
      const endDate = new Date(item.endDate);

      const lastDayOfMonth = new Date(
        startDate.getFullYear(),
        currentMonth,
        0
      ).getDate();

      startDay = startDate.getDate();
      endDay = endDate.getDate();

      if (startDate.getMonth() !== endDate.getMonth()) {
        if (
          endDate.getFullYear() > startDate.getFullYear() ||
          (endDate.getFullYear() === startDate.getFullYear() &&
            endDate.getMonth() > startDate.getMonth())
        ) {
          if (currentMonth === endDate.getMonth() + 1) {
            startDay = 1; // 지난달이면 startDay를 1로 설정
          } else {
            endDay = lastDayOfMonth;
          }
        }
      }

      if (
        startDate.getMonth() + 1 !== currentMonth &&
        endDate.getMonth() + 1 !== currentMonth
      ) {
        startDay = 0;
        endDay = 0;
      }

      if (!organizedData[item.userName]) {
        organizedData[item.userName] = {
          ...item,
          periods: [{ startDay, endDay, taskName: item.taskName }],
        };
      } else {
        organizedData[item.userName].periods.push({
          startDay,
          endDay,
          taskName: item.taskName,
        });
      }
    });

    return Object.values(organizedData);
  };

  return (
    <div className="calendar-container">
      <CalendarTable
        users={userData}
        month={currentMonth}
        tasks={combinedTasks}
      />
    </div>
  );
}

export default Calendar;
