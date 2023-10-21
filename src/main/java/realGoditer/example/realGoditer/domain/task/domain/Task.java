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

    private double videoLength;
    private double incentiveAmount;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;  // enum TaskStatus { IN_PROGRESS, COMPLETED }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    // Protected constructor for the factory method
    protected Task(double videoLength, double incentiveAmount, LocalDate startDate, TaskList taskList) {
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.startDate = startDate;
        this.taskList = taskList;
        this.status = TaskStatus.IN_PROGRESS;
    }

    // Static factory method
    public static Task from(double videoLength, double incentiveAmount, LocalDate startDate, TaskList taskList) {
        return new Task(videoLength, incentiveAmount, startDate, taskList);
    }

    // 생략: Setter, 기타 메서드...
}
