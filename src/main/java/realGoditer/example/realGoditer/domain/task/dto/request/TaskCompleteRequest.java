package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskCompleteRequest {

    private String name;
    private Double videoLength;
    private Double incentiveAmount;
    private LocalDate startDate;
    private TaskStatus status;


    public static TaskCompleteRequest of(
            final String name,
            final double videoLength,
            final double incentiveAmount,
            final LocalDate startDate,
            final TaskStatus status
    ) {
        return new TaskCompleteRequest(
                name,
                videoLength,
                incentiveAmount,
                startDate,
                status);
    }

}
