package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.util.List;

public interface TaskListService {

    public TaskList findMonthlyTaskList();

    TaskList findMonthlyTaskListForUser(User user);

    List<TaskList> getAllTaskListsForUser(Long userId);

    List<TaskList> getAllTaskLists();

}
