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
//    @ApiModelProperty(
//            name = "User Id", //Этот параметр вообще никак не отображается, поэтому далее я его указывать не буду
//            example = "1",
//            value = "Id пользователя, которому отправляется комментарий"
//    )
//    @Min(value = 1, message = "Id пользователя не может быть меньше 1")
//    private Integer userId;

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
