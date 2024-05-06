package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInternship {
    private String internshipName;
    private String description;
    private Timestamp startDate;
    private Timestamp signEndDate;
}
