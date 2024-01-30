package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.util.List;

public interface TaskListService {

    TaskList findMonthlyTaskList(int year, int month);

    TaskList findMonthlyTaskListForUser(User user);

    List<TaskList> getAllTaskListsForUser(Long userId);

    List<TaskList> getAllTaskLists();

    TaskList getTaskListByYearAndMonth(int year, int month);

}
