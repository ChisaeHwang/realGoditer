package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

public interface TaskListService {

    TaskList findMonthlyTaskListForUser(User user);

}
