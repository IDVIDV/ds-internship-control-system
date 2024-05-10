package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе успеваемости")
public class Performance {
    @ApiModelProperty(
            example = "http://gitlab.example.com/path/to/fork",
            value = "Ссылка на форк в gitlab"
    )
    private String url;

    @ApiModelProperty(
            example = "false",
            value = "Оценка"
    )
    private Boolean accepted;
}
