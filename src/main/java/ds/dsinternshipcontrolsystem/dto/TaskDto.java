package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer id;
    private Integer lessonId;
    private String taskName;
    private String description;
    private String url;
    private List<TaskForkDto> taskForkDtoList;
}
