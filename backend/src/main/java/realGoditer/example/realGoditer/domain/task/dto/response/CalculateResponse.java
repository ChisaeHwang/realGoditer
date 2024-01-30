package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CalculateResponse {

    private String userName;          // 유저 이름
    private double totalVideoLength;  // 한달 동안 만든 영상 길이
    private double payPerMinute;      // 분당 페이
    private double totalIncentive;    // 인센티브
    private double monthlySalary;     // 이번달 급여
    private double deductedAmount;    // 이번달 3.3% 징수한 금액
    private double netMonthlySalary;  // 이번달 실급여

    // Static factory method
    public static CalculateResponse from(
            String userName,
            double totalVideoLength,
            double payPerMinute,
            double totalIncentive,
            double monthlySalary,
            double deductedAmount,
            double netMonthlySalary) {

        return new CalculateResponse(
                userName,
                totalVideoLength,
                payPerMinute,
                totalIncentive,
                monthlySalary,
                deductedAmount,
                netMonthlySalary);

    }
}
