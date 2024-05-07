package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    private String username;
    private String password;
    private String mail;
    private String fullName;
    private String phone;
    private String telegramId;
    private String studyStatus;
    private String university;
    private String faculty;
    private String specialization;
    private Integer course;
}
