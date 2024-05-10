package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, необходимые для добавления нового задания")
public class AddTask {
    @ApiModelProperty(
            example = "1",
            value = "Id занятия, в которое добавляется задание"
    )
    @Min(value = 1, message = "Id занятия не может быть меньше 1")
    private Integer lessonId;

    @ApiModelProperty(
            example = "Task Name Example",
            value = "Название задания"
    )
    @NotBlank(message = "Название задания не может быть пустым")
    private String taskName;

    @ApiModelProperty(
            example = "Task Description Example",
            value = "Описание задания"
    )
    private String description;

    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/project",
            value = "Полная ссылка на эталонный репозиторий задания"
    )
    @NotBlank(message = "Ссылка на эталонный репозиторий задания не может быть пустой")
    private String url;

    @ApiModelProperty(
            example = "path/to/project",
            value = "Путь к эталонному репозиторию задания без домена"
    )
    @NotBlank(message = "Путь к эталонному репозиторию задания не может быть пустым")
    private String path;
}
