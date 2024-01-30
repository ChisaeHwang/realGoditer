package realGoditer.example.realGoditer.domain.task.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.task.domain.TaskStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // Jackson JSON 변환을 위한 기본 생성자
public class TaskUpdateRequest {

    private String name;
    private Double videoLength; // 초로 변환된 비디오 길이
    private Double incentiveAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
    private String remarks; // 추가된 필드
    private String creator; // 추가된 필드
    private Role role; // 추가된 필드
    private Double pay; // 추가된 필드

    // 기존의 of() 메소드는 제거하거나 아래와 같이 업데이트할 수 있습니다.
    // 이 경우 클라이언트는 이미 분을 초로 변환한 값을 videoLength로 전달하면 됩니다.
    public static TaskUpdateRequest of(
            String name,
            Double videoLength,
            Double incentiveAmount,
            LocalDate startDate,
            LocalDate endDate,
            TaskStatus status,
            String remarks,
            String creator,
            Role role,
            Double pay
    ) {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.name = name;
        request.videoLength = videoLength;
        request.incentiveAmount = incentiveAmount;
        request.startDate = startDate;
        request.endDate = endDate;
        request.status = status;
        request.remarks = remarks;
        request.creator = creator;
        request.role = role;
        request.pay = pay;
        return request;
    }
}
