package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе всех форков задания")
public class TaskForkItem {
    @ApiModelProperty(
            example = "1",
            value = "Id форка"
    )
    private Integer id;

    @ApiModelProperty(
            example = "1",
            value = "Id задания, от которого сделан форк"
    )
    private Integer taskId;

    @ApiModelProperty(
            example = "1",
            value = "Id пользователя, которому принадлежит форк"
    )
    private Integer userId;

    @ApiModelProperty(
            example = "false",
            value = "Оценка"
    )
    private Boolean accepted;

    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/project",
            value = "Ссылка на форк-репозиторий в gitlab"
    )
    private String url;
}
