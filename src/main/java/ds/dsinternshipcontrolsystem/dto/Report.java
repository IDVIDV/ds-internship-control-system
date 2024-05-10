package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложение при запросе отчета по стажировке")
public class Report {
    @ApiModelProperty(
            value = "Участники на момент составления отчета"
    )
    private List<UserDto> users;

    @ApiModelProperty(
            value = "Опубликованные задания на момент составления отчета"
    )
    private List<TaskDto> tasks;
}
