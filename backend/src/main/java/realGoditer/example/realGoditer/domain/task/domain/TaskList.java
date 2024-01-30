package realGoditer.example.realGoditer.domain.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.member.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "task_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    // Protected constructor for builder
    protected TaskList(int year, int month, User user) {
        this.year = year;
        this.month = month;
        this.user = user;
    }

    protected TaskList(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public static TaskList empty() {
        return new TaskList();
    }

    public static TaskList from(
            int year,
            int month,
            User user) {
        return new TaskList(year, month, user);
    }

    public static TaskList from(
            int year,
            int month) {
        return new TaskList(year, month);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }


}
