package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Integer id;
    private Timestamp sendDate;
    private String messageContent;
    private String senderUsername;
    private String receiverUsername;
}
