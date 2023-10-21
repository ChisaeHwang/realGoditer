package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.task.domain.Task;

public interface  TaskService {

    Task addTaskToTaskList(double videoLength, double incentiveAmount, Long userId);

}
