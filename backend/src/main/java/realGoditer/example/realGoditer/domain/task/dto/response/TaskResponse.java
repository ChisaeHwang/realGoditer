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
public class TaskResponse {
    private Long id;
    private String name;
    private double videoLength;
    private double incentiveAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Role role;
    private String creator;
    private TaskStatus status;
    private double pay;


    public static TaskResponse from(Task task, Role role) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setVideoLength(task.getVideoLength());
        response.setIncentiveAmount(task.getIncentiveAmount());
        response.setStartDate(task.getStartDate());
        response.setEndDate(task.getEndDate());
        response.setPay(task.getTempPay());
        response.setRole(role);
        response.setCreator(task.getCreator());
        response.setStatus(task.getStatus());
        return response;
    }

    public static TaskResponse from(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setVideoLength(task.getVideoLength());
        response.setIncentiveAmount(task.getIncentiveAmount());
        response.setStartDate(task.getStartDate());
        response.setEndDate(task.getEndDate());
        response.setPay(task.getTempPay());
        response.setCreator(task.getCreator());
        response.setStatus(task.getStatus());
        return response;
    }

    public static List<TaskResponse> fromList(List<Task> tasks, List<User> users) {
        // User 목록을 Map으로 변환하여 검색 속도 향상
        Map<String, Role> userRoleMap = users.stream()
                .collect(Collectors.toMap(User::getName, User::getRole));

        // Task와 User를 매핑하여 TaskResponse 생성
        List<TaskResponse> responses = tasks.stream()
                .filter(task -> userRoleMap.containsKey(task.getCreator()))
                .map(task -> from(task, userRoleMap.get(task.getCreator())))
                .collect(Collectors.toList());
        return responses;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }
}

