package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskAddRequest {

    private double videoLength;
    private double incentiveAmount;

    public static TaskAddRequest of(final double videoLength, final double incentiveAmount) {
        return new TaskAddRequest(videoLength, incentiveAmount);
    }
}
