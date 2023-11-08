package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.member.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskCompleteRequest {

    private String name;
    private Double videoLength;
    private Double incentiveAmount;
    private Double pay;
    private Role role;
    private String remark;


    public static TaskCompleteRequest of(
            final String name,
            final double videoLength,
            final double incentiveAmount,
            final double pay,
            final Role role,
            final String remark
    ) {
        return new TaskCompleteRequest(
                name,
                videoLength,
                incentiveAmount,
                pay,
                role,
                remark);
    }

}
