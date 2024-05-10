package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddComment {
    @Min(1)
    private Integer userId;
    @Min(1)
    private Integer commitId;
    private String commentContent;
}
