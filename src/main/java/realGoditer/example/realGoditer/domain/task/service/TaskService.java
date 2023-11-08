package realGoditer.example.realGoditer.domain.task.service;

import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
import realGoditer.example.realGoditer.domain.task.dto.request.*;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateDetailResponse;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;

import java.util.List;

public interface  TaskService {

    Task addTaskToTaskList(TaskAddRequest request, Long userId);

    Task getTask(Long taskId, Long userId);

    List<Task> getMonthTask(TaskRequest request);

    List<CalculateResponse> getCalculate(CalculateRequest request);

    List<CalculateDetailResponse> getDetailCalculate(CalculateDetailRequest request);

    void deleteTask(Long taskId, Long userId);

    Task updateTask(Long taskId, TaskUpdateRequest request, Long userId);

    Task completeTask(Long taskId, TaskCompleteRequest request, Long userId);
}
