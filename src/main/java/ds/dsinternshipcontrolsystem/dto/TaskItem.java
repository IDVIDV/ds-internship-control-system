package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе всех заданий занятия")
public class TaskItem {
    @ApiModelProperty(
            example = "1",
            value = "Id задания"
    )
    private Integer id;

    @ApiModelProperty(
            example = "1",
            value = "Id занятия, которому принадлежит задание"
    )
    private Integer lessonId;

    @ApiModelProperty(
            example = "Task Name Example",
            value = "Название задания"
    )
    private String taskName;

    @ApiModelProperty(
            example = "Task Description Example",
            value = "Описание задания"
    )
    private String description;

    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/project",
            value = "Ссылка на эталонный репозиторий в gitlab"
    )
    private String url;
}
