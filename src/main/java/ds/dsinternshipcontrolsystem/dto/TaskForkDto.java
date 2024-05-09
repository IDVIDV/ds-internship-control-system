package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskForkDto {
    private Integer id;
    private Integer taskId;
    private Integer userId;
    private Boolean accepted;
    private String url;
    private List<CommitDto> commitDtoList;
}
