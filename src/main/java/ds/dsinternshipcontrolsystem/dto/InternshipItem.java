package ds.dsinternshipcontrolsystem.dto;

import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе всех стажировок")
public class InternshipItem {
    @ApiModelProperty(
            example = "1",
            value = "Id стажировки"
    )
    private Integer id;

    @ApiModelProperty(
            example = "Internship Name Example",
            value = "Название стажировки"
    )
    private String internshipName;

    @ApiModelProperty(
            example = "Internship Description Example",
            value = "Описание стажировки"
    )
    private String description;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-10T08:00:00",
            value = "Дата начала стажировки"
    )
    private Timestamp startDate;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-10T08:00:00",
            value = "Дата окончания регистрации на стажировку"
    )
    private Timestamp signEndDate;

    @ApiModelProperty(
            example = "REGISTRY",
            value = "Статус стажировки"
    )
    private InternshipStatus status;
}
