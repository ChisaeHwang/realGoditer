package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CalculateDetailResponse {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double pay;
    private String remarks;


    public static CalculateDetailResponse from(Task task) {
        CalculateDetailResponse response = new CalculateDetailResponse();
        response.setTaskName(task.getName());
        response.setStartDate(task.getStartDate());
        response.setEndDate(task.getEndDate());
        response.setPay(task.getTempPay());
        response.setRemarks(task.getRemark());
        return response;
    }

    public static List<CalculateDetailResponse> fromList(List<Task> tasks) {
        List<CalculateDetailResponse> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(from(task));
        }
        return responses;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
