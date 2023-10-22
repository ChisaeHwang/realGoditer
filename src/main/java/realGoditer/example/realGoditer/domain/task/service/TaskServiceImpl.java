package realGoditer.example.realGoditer.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskAddRequest;
import realGoditer.example.realGoditer.domain.task.dto.request.TaskUpdateRequest;
import realGoditer.example.realGoditer.global.exception.UnauthorizedException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskListService taskListService;

    @Override
    public Task addTaskToTaskList(TaskAddRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        TaskList taskList = taskListService.findMonthlyTaskList();

        Task task = Task.from(
                request.getName(),
                request.getVideoLength(),
                request.getIncentiveAmount(),
                LocalDate.now(),
                user.getName(),
                taskList);

        taskList.addTask(task);

        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " doesn't exist."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        if (!task.getCreator().equals(user.getName())) {
            throw new UnauthorizedException("Only the creator of the task can delete it."); // UnauthorizedException는 적절한 예외 처리 클래스를 사용하거나 직접 정의해야 합니다.
        }

        return task;
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        // taskId를 사용하여 작업을 찾습니다.
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " doesn't exist."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));


        // 작업의 생성자와 현재 사용자의 이름을 비교합니다.
        if (!task.getCreator().equals(user.getName())) {
            throw new UnauthorizedException("Only the creator of the task can delete it."); // UnauthorizedException는 적절한 예외 처리 클래스를 사용하거나 직접 정의해야 합니다.
        }

        taskRepository.delete(task);
    }


    @Override
    public Task updateTask(Long taskId, TaskUpdateRequest request, Long userId) {
        // taskId를 사용하여 작업을 찾고, 찾은 작업의 속성을 수정한 후 저장합니다.
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " doesn't exist."));

        task.taskUpdate(
                request.getName(),
                request.getVideoLength(),
                request.getIncentiveAmount(),
                request.getStartDate(),
                request.getStatus()
        );

        return taskRepository.save(task);
    }

}
