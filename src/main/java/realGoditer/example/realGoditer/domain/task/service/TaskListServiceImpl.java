package realGoditer.example.realGoditer.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskListRepository;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    @Override
    public TaskList findMonthlyTaskListForUser(User user) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        return taskListRepository.findByYearAndMonthAndUser(currentYear, currentMonth, user)
                .orElseGet(() -> {
                    TaskList newTaskList = TaskList.from(currentYear, currentMonth, user);
                    return taskListRepository.save(newTaskList);
                });
    }
}
