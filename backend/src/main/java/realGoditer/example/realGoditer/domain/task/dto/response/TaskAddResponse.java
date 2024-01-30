package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskAddResponse {

    private Long id;
    private String name;
    private double videoLength;
    private double incentiveAmount;
    private double pay;
    private LocalDate startDate;
    private TaskStatus status;
    private String creator;
    private Long taskListId;

    public TaskAddResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.videoLength = task.getVideoLength();
        this.incentiveAmount = task.getIncentiveAmount();
        this.startDate = task.getStartDate();
        this.creator = task.getCreator();
        this.pay = task.getTempPay();
        this.status = task.getStatus();
        this.taskListId = task.getTaskList().getId();
    }

    // Static factory method
    public static TaskAddResponse from(Task task) {
        return new TaskAddResponse(task);
    }

}
