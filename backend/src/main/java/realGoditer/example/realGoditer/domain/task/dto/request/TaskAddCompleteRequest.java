package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.member.domain.Role;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskAddCompleteRequest {

    private String name;
    private Double videoLength;
    private Double incentiveAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double pay;
    private Role role;
    private String remark;

    public static TaskAddCompleteRequest of(
            final String name,
            final double videoLength,
            final double incentiveAmount,
            final LocalDate startDate,
            final LocalDate endDate,
            final double pay,
            final Role role,
            final String remark) {
        return new TaskAddCompleteRequest( name,
                videoLength,
                incentiveAmount,
                startDate,
                endDate,
                pay,
                role,
                remark);
    }
}
