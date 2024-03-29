package realGoditer.example.realGoditer.domain.task.util;

import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.task.dao.TaskRepository;
import realGoditer.example.realGoditer.domain.task.domain.Task;
import realGoditer.example.realGoditer.domain.task.dto.response.CalculateResponse;

import java.util.List;

public class SalaryCalculator {

    public static CalculateResponse calculateForUser(User user, Long taskId, TaskRepository taskRepository) {
        List<Task> tasksForUser = taskRepository.findByCreatorAndTaskListId(user.getName(), taskId);

        double firstVideoLength = tasksForUser.stream().mapToDouble(Task::getVideoLength).sum();
        double totalVideoLength = tasksForUser.stream().mapToDouble(Task::getVideoLength).sum();
        double totalIncentive = tasksForUser.stream().mapToDouble(Task::getIncentiveAmount).sum();

        double totalSpecialPay = 0;

        Long pay = user.getPay() != null ? user.getPay() : 0L; // 기본값으로 0 설정

        for(Task task : tasksForUser) {
            if (task.getTempPay() != pay) {
                totalVideoLength -= task.getVideoLength();
                totalSpecialPay += Math.floor(task.getVideoLength() / 60) * task.getTempPay();
            }
        }

        double monthlySalary = Math.floor(totalVideoLength / 60) * pay + totalIncentive + totalSpecialPay;

        double rawIncomeTax = monthlySalary * 0.03;
        double incomeTax = Math.floor(rawIncomeTax / 10) * 10;  // 1의 자리수를 제거

        double rawLocalTax = monthlySalary * 0.003;
        double localTax = Math.floor(rawLocalTax / 10) * 10;  // 1의 자리수를 제거

        double totalDeduction = incomeTax + localTax;
        double netMonthlySalary = monthlySalary - totalDeduction;

        return CalculateResponse.from(
                user.getName(),
                firstVideoLength,
                pay,
                totalIncentive,
                monthlySalary,
                totalDeduction,
                netMonthlySalary
        );
    }
}



