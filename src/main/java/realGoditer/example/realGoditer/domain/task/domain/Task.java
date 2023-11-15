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
    private double tempPay;
    private LocalDate startDate;
    private LocalDate endDate;
    private String creator;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;  // enum TaskStatus { IN_PROGRESS, COMPLETED }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
    private String remark;

    protected Task(
            String name,
            double videoLength,
            double incentiveAmount,
            LocalDate startDate,
            LocalDate endDate,
            String creator,
            double pay,
            TaskList taskList) {
        this.name = name;
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creator = creator;
        this.taskList = taskList;
        this.tempPay = pay;
        this.status = TaskStatus.IN_PROGRESS;
    }


    public static Task from(
            String name,
            double videoLength,
            double incentiveAmount,
            double pay,
            LocalDate startDate,
            LocalDate endDate,
            String creator,
            TaskList taskList) {
        return new Task(
                name,
                videoLength,
                incentiveAmount,
                startDate,
                endDate,
                creator,
                pay,
                taskList);
    }

    public void taskUpdate(
            String name,
            Double videoLength,
            Double incentiveAmount,
            LocalDate startDate,
            LocalDate endDate,
            TaskStatus status,
            Double tempPay,
            String creator,
            String remark,
            TaskList taskList) {
        this.name = name;
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.tempPay = tempPay;
        this.creator = creator;
        this.remark = remark;
        this.taskList = taskList;
    }

    public void taskComplete(
            String name,
            Double videoLength,
            Double incentiveAmount,
            Double tempPay,
            String remark,
            TaskList taskList

    ) {
        this.name = name;
        this.videoLength = videoLength;
        this.incentiveAmount = incentiveAmount;
        this.tempPay = tempPay;
        this.remark = remark;
        this.status = TaskStatus.COMPLETED;
        this.taskList = taskList;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }
}
