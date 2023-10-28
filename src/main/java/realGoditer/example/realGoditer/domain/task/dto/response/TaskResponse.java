package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskResponse {
    private Long id;
    private String name;
    private double videoLength;
    private double incentiveAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String creator;
    private TaskStatus status;


    public static TaskResponse from(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setVideoLength(task.getVideoLength());
        response.setIncentiveAmount(task.getIncentiveAmount());
        response.setStartDate(task.getStartDate());
        response.setEndDate(task.getEndDate());
        response.setCreator(task.getCreator());
        response.setStatus(task.getStatus());
        return response;
    }

    public static List<TaskResponse> fromList(List<Task> tasks) {
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(from(task));
        }
        return responses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVideoLength(double videoLength) {
        this.videoLength = videoLength;
    }

    public void setIncentiveAmount(double incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}

