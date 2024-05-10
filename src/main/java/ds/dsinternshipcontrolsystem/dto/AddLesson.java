package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLesson {
    @Min(1)
    private Integer internshipId;
    @NotBlank
    private String lessonName;
    private String description;
}
