package realGoditer.example.realGoditer.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskListRepository;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    private final UserRepository userRepository;

    @Override
    public TaskList findMonthlyTaskList() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        return taskListRepository.findByYearAndMonth(currentYear, currentMonth)
                .orElseGet(() -> {
                    TaskList newTaskList = TaskList.from(currentYear, currentMonth);
                    return taskListRepository.save(newTaskList);
                });
    }


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

    @Override
    public List<TaskList> getAllTaskListsForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        return taskListRepository.findByUser(user);
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }
}
