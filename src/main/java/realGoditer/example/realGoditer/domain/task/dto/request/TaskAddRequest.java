package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskAddRequest {

    private String name;
    private double videoLength;
    private double incentiveAmount;

    public static TaskAddRequest of(
            final String name,
            final double videoLength,
            final double incentiveAmount) {
        return new TaskAddRequest(name, videoLength, incentiveAmount);
    }
}
