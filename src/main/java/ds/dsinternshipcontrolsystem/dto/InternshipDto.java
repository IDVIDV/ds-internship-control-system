package ds.dsinternshipcontrolsystem.dto;

import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipDto {
    private Integer id;
    private String internshipName;
    private String description;
    private Timestamp startDate;
    private Timestamp signEndDate;
    private InternshipStatus status;
    private List<LessonDto> lessonDtos;
}
