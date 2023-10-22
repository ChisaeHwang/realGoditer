package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskAddRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskUpdateRequest;

public interface  TaskService {

    Task addTaskToTaskList(TaskAddRequest request, Long userId);

    void deleteTask(Long taskId, Long userId);

    Task updateTask(Long taskId, TaskUpdateRequest request, Long userId);
}
