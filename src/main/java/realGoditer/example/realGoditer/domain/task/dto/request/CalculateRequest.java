package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CalculateRequest {

    private Integer month;
    private Integer year;

}
