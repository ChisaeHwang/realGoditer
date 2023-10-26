package realGoditer.example.realGoditer.domain.task.util;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.dto.request.CalculateRequest;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;

import java.util.List;

public class SalaryCalculator {

    public static CalculateResponse calculateForUser(User user, Long taskId, TaskRepository taskRepository) {
        List<Task> tasksForUser = taskRepository.findByCreatorAndTaskListId(user.getName(), taskId);

        double totalVideoLength = tasksForUser.stream().mapToDouble(Task::getVideoLength).sum();
        double totalIncentive = tasksForUser.stream().mapToDouble(Task::getIncentiveAmount).sum();

        double monthlySalary = (totalVideoLength * user.getPay()) + totalIncentive;

        double rawIncomeTax = monthlySalary * 0.03;
        double incomeTax = Math.floor(rawIncomeTax / 10) * 10;  // 1의 자리수를 제거

        double rawLocalTax = monthlySalary * 0.003;
        double localTax = Math.floor(rawLocalTax / 10) * 10;  // 1의 자리수를 제거

        double totalDeduction = incomeTax + localTax;
        double netMonthlySalary = monthlySalary - totalDeduction;

        return CalculateResponse.from(
                user.getName(),
                totalVideoLength,
                user.getPay(),
                totalIncentive,
                monthlySalary,
                totalDeduction,
                netMonthlySalary
        );
    }
}



