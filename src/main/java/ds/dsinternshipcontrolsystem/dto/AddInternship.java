package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, необходимые для добавления новой стажировки")
public class AddInternship {
    @ApiModelProperty(
            example = "Internship Name Example",
            value = "Название стажировки"
    )
    @NotBlank(message = "Название стажировки не может быть пустым")
    private String internshipName;

    @ApiModelProperty(
            example = "Internship Description example",
            value = "Описание стажировки"
    )
    private String description;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-08T08:00:00",
            value = "Дата начала стажировки"
    )
    @NotNull(message = "Дата начала стажировки не может быть пустой")
    private Timestamp startDate;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-08T08:00:00",
            value = "Дата окончания регистрации на стажировку"
    )
    @NotNull(message = "Дата окончания регистрации не может быть пустой")
    private Timestamp signEndDate;
}
