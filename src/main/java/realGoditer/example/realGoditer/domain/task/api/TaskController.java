package realGoditer.example.realGoditer.domain.task.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
import realGoditer.example.realGoditer.domain.task.dto.request.CalculateRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskAddRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskUpdateRequest;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;
import realGoditer.example.realGoditer.domain.task.dto.response.TaskAddResponse;
import realGoditer.example.realGoditer.domain.task.dto.response.TaskListResponse;
import realGoditer.example.realGoditer.domain.task.dto.response.TaskResponse;
import realGoditer.example.realGoditer.domain.task.service.TaskListService;
import realGoditer.example.realGoditer.domain.task.service.TaskService;
import realGoditer.example.realGoditer.global.dto.ApiResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    private final TaskListService taskListService;

    @Member
    @PostMapping("/add")
    public ApiResponse<TaskAddResponse> addTask(@Authenticated AuthPrincipal authPrincipal,
                                        @Valid @RequestBody final TaskAddRequest request) {

        Task task = taskService.addTaskToTaskList(request, authPrincipal.getId());

        return ApiResponse.success(TaskAddResponse.from(task), 200);
    }

    @GetMapping("/all")
    public ApiResponse<List<TaskListResponse>> getAllTask() {

        List<TaskList> lists = taskListService.getAllTaskLists();

        return ApiResponse.success(TaskListResponse.fromList(lists), 200);
    }

    @GetMapping("/all/user")
    public ApiResponse<List<TaskListResponse>> getAllTask(
            @Authenticated AuthPrincipal authPrincipal) {

        List<TaskList> lists = taskListService.getAllTaskListsForUser(authPrincipal.getId());

        return ApiResponse.success(TaskListResponse.fromList(lists), 200);
    }

    @Member
    @GetMapping("/{taskId}")
    public ApiResponse<TaskResponse> getTask(
            @Authenticated AuthPrincipal authPrincipal,
            @PathVariable Long taskId
    ) {

        Task task = taskService.getTask(taskId, authPrincipal.getId());

        return ApiResponse.success(TaskResponse.from(task), 200);
    }

    @GetMapping("/calculate")
    public ApiResponse<List<CalculateResponse>> getCalculate(
            @Valid @RequestBody final CalculateRequest request
    ) {
        List<CalculateResponse> responses = taskService.getCalculate(request);
        return ApiResponse.success(responses, 200);
    }


    @Member
    @PostMapping("/update/{taskId}")
    public ApiResponse<TaskResponse> updateTask(
            @Authenticated AuthPrincipal authPrincipal,
            @PathVariable Long taskId,
            @Valid @RequestBody final TaskUpdateRequest request) {

        Task task = taskService.updateTask(taskId, request, authPrincipal.getId());

        return ApiResponse.success(TaskResponse.from(task), 200);
    }

    @Member
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<ApiResponse<String>> deleteTask(
            @Authenticated AuthPrincipal authPrincipal,
            @PathVariable Long taskId) {

        taskService.deleteTask(taskId, authPrincipal.getId());

        ApiResponse<String> body = ApiResponse.success("", HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


}
