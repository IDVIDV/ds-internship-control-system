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
@ApiModel(description = "Данные, отправляемые приложением при запросе сообщения")
public class MessageDto {
    @ApiModelProperty(
            example = "1",
            value = "Id сообщения"
    )
    private Integer id;

    @ApiModelProperty(
            dataType = "String",
            example = "2023-05-10T08:00:00",
            value = "Дата отправки сообщения"
    )
    private Timestamp sendDate;

    @ApiModelProperty(
            example = "Message Content Example",
            value = "Содержание сообщение"
    )
    private String messageContent;

    @ApiModelProperty(
            example = "senderusername",
            value = "Username отправителя"
    )
    private String senderUsername;

    @ApiModelProperty(
            example = "receiverusername",
            value = "Username получателя"
    )
    private String receiverUsername;
}
