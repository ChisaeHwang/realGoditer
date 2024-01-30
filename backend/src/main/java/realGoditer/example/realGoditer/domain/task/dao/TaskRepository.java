package realGoditer.example.realGoditer.domain.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import realGoditer.example.realGoditer.domain.task.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT t FROM Task t WHERE t.creator = :creator AND t.taskList.id = :taskListId")
    List<Task> findByCreatorAndTaskListId(@Param("creator") String creator, @Param("taskListId") Long taskListId);


    List<Task> findByTaskListId(Long taskListId);
}
