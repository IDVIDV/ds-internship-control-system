package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложение при запросе комментария")
public class CommentDto {
    @ApiModelProperty(
            example = "1",
            value = "Id запрашиваемого комментария"
    )
    private Integer id;

    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/project/-/commit/hash",
            value = "Ссылка на коммит в gitlab"
    )
    private String commitUrl;

    @ApiModelProperty(
            example = "Comment Content Example",
            value = "Содержание комментария"
    )
    private String commentContent;
}
