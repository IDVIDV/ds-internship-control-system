package ds.dsinternshipcontrolsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Данные, отправляемые приложением при запросе пользователя")
public class UserDto {
    @ApiModelProperty(
            example = "1",
            value = "Id пользователя"
    )
    private Integer id;

    @ApiModelProperty(
            example = "username",
            value = "Ник пользователя"
    )
    private String username;

    @ApiModelProperty(
            example = "mail@example.com",
            value = "E-mail пользователя"
    )
    private String mail;

    @ApiModelProperty(
            example = "Ивакин Даниил Вадимович",
            value = "ФИО пользователя"
    )
    private String fullName;

    @ApiModelProperty(
            example = "+71111111111",
            value = "Телефон пользователя"
    )
    private String phone;

    @ApiModelProperty(
            example = "@IDV",
            value = "TelegramId пользователя"
    )
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
