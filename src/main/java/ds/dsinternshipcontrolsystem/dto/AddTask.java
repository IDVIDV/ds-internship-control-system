package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTask {
    @Min(1)
    private Integer lessonId;
    @NotBlank
    private String taskName;
    private String description;
    @NotBlank
    private String url;
    @NotBlank
    private String path;
}
