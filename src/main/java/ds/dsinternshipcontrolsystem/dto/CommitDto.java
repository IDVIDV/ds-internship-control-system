package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitDto {
    private Integer id;
    private Integer taskForkId;
    private Integer taskId;
    private String taskName;
    private Integer userId;
    private String username;
    private Timestamp commitDate;
    private String author;
    private String url;
}
