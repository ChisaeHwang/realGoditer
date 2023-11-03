package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
import realGoditer.example.realGoditer.domain.task.dto.request.CalculateRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskAddRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskUpdateRequest;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;

import java.util.List;

public interface  TaskService {

    Task addTaskToTaskList(TaskAddRequest request, Long userId);

    Task getTask(Long taskId, Long userId);

    List<Task> getMonthTask(TaskRequest request);

    List<CalculateResponse> getCalculate(CalculateRequest request);

    void deleteTask(Long taskId, Long userId);

    Task updateTask(Long taskId, TaskUpdateRequest request, Long userId);
}
