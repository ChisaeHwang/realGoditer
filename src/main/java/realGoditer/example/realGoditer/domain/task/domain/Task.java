package realGoditer.example.realGoditer.domain.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "task")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double videoLength;
    private double incentiveAmount;
    private LocalDate startDate;

    private String creator;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;  // enum TaskStatus { IN_PROGRESS, COMPLETED }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    // Protected constructor for the factory method
    protected Task(
            String name,
            double videoLength,
            double incentiveAmount,
            LocalDate startDate,
            String creator,
            TaskList taskList) {
        this.name = name;
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.startDate = startDate;
        this.creator = creator;
        this.taskList = taskList;
        this.status = TaskStatus.IN_PROGRESS;
    }

    // Static factory method
    public static Task from(
            String name,
            double videoLength,
            double incentiveAmount,
            LocalDate startDate,
            String creator,
            TaskList taskList) {
        return new Task(
                name,
                videoLength,
                incentiveAmount,
                startDate,
                creator,
                taskList);
    }

    public void taskUpdate(
            String name,
            Double videoLength,
            Double incentiveAmount,
            LocalDate startDate,
            TaskStatus status) {
        this.name = name;
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.startDate = startDate;
        this.status = status;
    }
}
