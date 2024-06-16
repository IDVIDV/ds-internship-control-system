package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе всех занятий стажировки")
public class LessonItem {
    @ApiModelProperty(
            example = "1",
            value = "Id занятия"
    )
    private Integer id;

    @ApiModelProperty(
            example = "1",
            value = "Id стажировки, которой принадлежит задание"
    )
    private Integer internshipId;

    @ApiModelProperty(
            example = "Lesson Name Example",
            value = "Название занятие"
    )
    private String lessonName;

    @ApiModelProperty(
            example = "Lesson Description Example",
            value = "Описание занятия"
    )
    private String description;
}
