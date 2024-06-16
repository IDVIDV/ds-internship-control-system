package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, необходимые для добавления нового комментария")
public class AddComment {
    @ApiModelProperty(
            example = "1",
            value = "Id коммита, которому ставится комментарий"
    )
    @Min(value = 1, message = "Id коммита не может быть меньше 1")
    private Integer commitId;

    @ApiModelProperty(
            example = "Comment Example",
            value = "Содержание комментария"
    )
    private String commentContent;
}
