package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Данные, необходмые для добавления нового занятия")
public class AddLesson {
    @ApiModelProperty(
            example = "1",
            value = "Id стажировки, в которую добавляется занятие"
    )
    @Min(value = 1, message = "Id стажировки не может быть меньше 1")
    private Integer internshipId;

    @ApiModelProperty(
            example = "Lesson Name Example",
            value = "Название стажировки"
    )
    @NotBlank(message = "Название занятия не может быть пустым")
    private String lessonName;

    @ApiModelProperty(
            example = "Lesson Description Example",
            value = "Описание занятия"
    )
    private String description;
}
