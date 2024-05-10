package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе коммита")
public class CommitDto {
    @ApiModelProperty(
            example = "1",
            value = "Id стажировки, в которую добавляется занятие"
    )
    private Integer id;

    @ApiModelProperty(
            example = "1",
            value = "Id форка, которому принадлежит коммит"
    )
    private Integer taskForkId;

    @ApiModelProperty(
            example = "1",
            value = "Id задания, от которого сделан форк"
    )
    private Integer taskId;

    @ApiModelProperty(
            example = "Task Name Example",
            value = "Название задания"
    )
    private String taskName;

    @ApiModelProperty(
            example = "1",
            value = "Id пользователя, которому принадлежит форк"
    )
    private Integer userId;

    @ApiModelProperty(
            example = "1",
            value = "Username пользователя"
    )
    private String username;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-10T08:00:00",
            value = "Дата и время коммита"
    )
    private Timestamp commitDate;

    @ApiModelProperty(
            example = "Author Example",
            value = "Автор коммита (отличается от username)"
    )
    private String author;

    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/project/-/commit/hash",
            value = "Ссылка на коммит в gitlab"
    )
    private String url;
}
