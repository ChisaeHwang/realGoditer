package realGoditer.example.realGoditer.domain.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    Optional<TaskList> findByYearAndMonthAndUser(int year, int month, User user);

}
