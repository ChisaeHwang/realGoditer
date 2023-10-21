package realGoditer.example.realGoditer.domain.task.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskAddRequest;
import realGoditer.example.realGoditer.domain.task.service.TaskService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @Member
    @PostMapping("/add")
    public ResponseEntity<Double> addTask(@Authenticated AuthPrincipal authPrincipal,
                                        @Valid @RequestBody final TaskAddRequest request) {

        log.info("api 호출 완료");

        Task task = taskService.addTaskToTaskList(
                request.getVideoLength(),
                request.getIncentiveAmount(),
                authPrincipal.getId());

        return ResponseEntity.ok(task.getVideoLength());
    }

}
