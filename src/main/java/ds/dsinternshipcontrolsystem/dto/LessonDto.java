package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Integer id;
    private Integer internshipId;
    private String lessonName;
    private String description;
    private List<TaskDto> taskDtoList;
}
