package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Данные, необходимые для регистрации нового пользователя")
public class RegisterUser {
    @ApiModelProperty(
            example = "username",
            value = "Ник пользователя"
    )
    @NotBlank(message = "Username не может быть пустым")
    private String username;

    @ApiModelProperty(
            example = "super-secret-pass",
            value = "Пароль пользователя"
    )
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @ApiModelProperty(
            example = "mail@example.com",
            value = "E-mail пользователя"
    )
    @NotNull(message = "E-mail не может быть пустым")
    @Email
    private String mail;

    @ApiModelProperty(
            example = "Ивакин Даниил Вадимович",
            value = "ФИО пользователя"
    )
    @NotBlank(message = "Имя не может быть пустым")
    private String fullName;

    @ApiModelProperty(
            example = "+71111111111",
            value = "Телефон пользователя"
    )
    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;

    @ApiModelProperty(
            example = "@IDV",
            value = "TelegramId пользователя"
    )
    @NotBlank(message = "TelegramId не может быть пустым")
    private String telegramId;

    @ApiModelProperty(
            example = "Studying",
            value = "Статус обучения пользователя"
    )
    private String studyStatus;

    @ApiModelProperty(
            example = "Voronezh State University",
            value = "Университет, в котором учится или учился пользователь"
    )
    private String university;

    @ApiModelProperty(
            example = "AMM",
            value = "Название факультета, на котором учится или учился пользователь"
    )
    private String faculty;

    @ApiModelProperty(
            example = "МОАИС",
            value = "Направление/Специализация пользователя"
    )
    private String specialization;

    @ApiModelProperty(
            example = "3",
            value = "Курс, на котором учится пользователь"
    )
    private Integer course;
}
