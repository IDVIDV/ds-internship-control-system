package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTask {
    private Integer lessonId;
    private String taskName;
    private String description;
    private String url;
    private String path;
}
