package realGoditer.example.realGoditer.domain.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
