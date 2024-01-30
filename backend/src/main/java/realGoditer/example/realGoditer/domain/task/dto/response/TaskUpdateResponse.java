package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskUpdateResponse {

    private Long id;
    private String name;
    private double videoLengthMinutes;
    private double videoLengthSeconds;
    private double incentiveAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Role role;
    private double pay;
    private String creator;
    private TaskStatus status;
    private String remark;


    public static TaskUpdateResponse from(Task task, User user) {
        TaskUpdateResponse response = new TaskUpdateResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setVideoLengthMinutes(task.getVideoLength() / 60);
        response.setVideoLengthSeconds(task.getVideoLength() % 60);
        response.setPay(task.getTempPay());
        response.setIncentiveAmount(task.getIncentiveAmount());
        response.setStartDate(task.getStartDate());
        response.setEndDate(task.getEndDate());
        response.setRole(user.getRole());
        response.setCreator(task.getCreator());
        response.setStatus(task.getStatus());
        response.setRemark(task.getRemark());
        return response;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setVideoLengthMinutes(double videoLengthMinutes) {
        this.videoLengthMinutes = videoLengthMinutes;
    }

    public void setVideoLengthSeconds(double videoLengthSeconds) {
        this.videoLengthSeconds = videoLengthSeconds;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
