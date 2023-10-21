package realGoditer.example.realGoditer.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
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
    public Task addTaskToTaskList(double videoLength, double incentiveAmount, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        TaskList taskList = taskListService.findMonthlyTaskListForUser(user);

        Task task = Task.from(videoLength, incentiveAmount, LocalDate.now(), taskList);

        taskList.addTask(task);

        return taskRepository.save(task);
    }

}
