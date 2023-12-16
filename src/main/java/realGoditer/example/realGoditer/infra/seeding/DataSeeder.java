package realGoditer.example.realGoditer.infra.seeding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskListRepository;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;
import realGoditer.example.realGoditer.domain.task.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private TaskRepository taskRepository;


    @Value("${app.database.seeding.enabled}")
    private boolean seedingEnabled;

    public void seedData() {
        if (!seedingEnabled) {
            return;
        }

//        // 시드 유저 생성
//        List<User> users = List.of(
//                User.of("조재홍", "userone@example.com", "password1", 0L, Role.EDITOR, "provider1"),
//                User.of("느대", "usertwo@example.com", "password2", 0L, Role.THUMBNAILER, "provider2"),
//                User.of("치피치피", "userthree@example.com", "password3", 0L, Role.EDITOR, "provider3")
//        );
//
//        users.forEach(userRepository::save);
//
//        // 시드 태스크리스트 생성
//        TaskList taskList = TaskList.from(2023, 12, users.get(0));
//        taskListRepository.save(taskList);
//
//
//        Task task1 = Task.from("비디오 편집", 10.0, 100.0, 10000, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 5), "조재홍", taskList);
//        Task task2 = Task.from("썸네일 디자인", 2.0, 50.0, 10000, LocalDate.of(2023, 12, 3), LocalDate.of(2023, 12, 4), "느대", taskList);
//        Task task3 = Task.from("영상 자막 작업", 5.0, 75.0, 10000, LocalDate.of(2023, 12, 2), LocalDate.of(2023, 12, 7), "치피치피", taskList);
//
//        task3.setStatus(TaskStatus.COMPLETED);
//
//        taskRepository.save(task1);
//        taskRepository.save(task2);
//        taskRepository.save(task3);
    }

    @Bean
    CommandLineRunner runSeeder() {
        return args -> {
            seedData();
        };
    }
}
