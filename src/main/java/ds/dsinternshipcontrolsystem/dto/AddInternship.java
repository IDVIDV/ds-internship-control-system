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
@ApiModel(description = "Entity send to add internship")
public class AddInternship {
    private String internshipName;
    private String description;
    @ApiModelProperty(
            dataType = "String",
            value = "2023-05-08T08:00:00",
            example = "2023-05-08T08:00:00"
    )
    private Timestamp startDate;
    @ApiModelProperty(
            value = "2023-05-08T08:00:00",
            dataType = "String",
            example = "2023-05-08T08:00:00"
    )
    private Timestamp signEndDate;
}
