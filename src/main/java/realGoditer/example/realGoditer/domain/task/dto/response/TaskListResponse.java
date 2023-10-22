package realGoditer.example.realGoditer.domain.task.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TaskListResponse {
    private Long id;
    private int year;
    private int month;
    private List<TaskResponse> tasks;

    public static List<TaskListResponse> fromList(List<TaskList> taskLists) {
        return taskLists.stream()
                .map(TaskListResponse::from)
                .collect(Collectors.toList());
    }

    public static TaskListResponse from(TaskList taskList) {
        TaskListResponse response = new TaskListResponse();
        response.setId(taskList.getId());
        response.setYear(taskList.getYear());
        response.setMonth(taskList.getMonth());
        response.setTasks(TaskResponse.fromList(taskList.getTasks()));
        return response;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }
}
