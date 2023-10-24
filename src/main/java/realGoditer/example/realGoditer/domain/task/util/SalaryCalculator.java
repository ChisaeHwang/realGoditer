package realGoditer.example.realGoditer.domain.task.util;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.dto.request.CalculateRequest;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;

import java.util.List;

public class SalaryCalculator {

    public static CalculateResponse calculateForUser(User user, CalculateRequest request, TaskRepository taskRepository) {
        List<Task> tasksForUser = taskRepository.findByCreatorAndTaskListId(user.getName(), request.getTaskListId());

        double totalVideoLength = tasksForUser.stream().mapToDouble(Task::getVideoLength).sum();
        double totalIncentive = tasksForUser.stream().mapToDouble(Task::getIncentiveAmount).sum();

        double monthlySalary = (totalVideoLength * user.getPay()) + totalIncentive;
        double deductedAmount = monthlySalary * 0.033;
        double netMonthlySalary = monthlySalary - deductedAmount;

        return CalculateResponse.from(
                user.getName(),
                totalVideoLength,
                user.getPay(),
                totalIncentive,
                monthlySalary,
                deductedAmount,
                netMonthlySalary
        );
    }
}

