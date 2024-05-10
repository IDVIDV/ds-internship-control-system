package ds.dsinternshipcontrolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    @Email
    private String mail;
    @NotBlank
    private String fullName;
    @NotBlank
    private String phone;
    @NotBlank
    private String telegramId;
    private String studyStatus;
    private String university;
    private String faculty;
    private String specialization;
    private Integer course;
}
