package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.MessageDto;
import ds.dsinternshipcontrolsystem.dto.Performance;
import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Api(tags = {"User Controller"}, description = "Обрабатывает запросы, связанные с пользователем")
public class UserController {
    private final UserService userService;

    @ApiOperation("Зарегистрироваться на стажировку")
    @PostMapping("/internship/{id}/register")
    public void signInInternship(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable("id")
            Integer internshipId) {
        userService.signInInternship(internshipId);
    }

    @ApiOperation("Получить сообщения, адресованные пользователю")
    @GetMapping("/user/messages")
    public ResponseEntity<List<MessageDto>> getMessages() {
        return new ResponseEntity<>(userService.getMessages(), HttpStatus.OK);
    }

    @ApiOperation("Получить успеваемость пользователя")
    @GetMapping("/user/performance")
    public ResponseEntity<List<Performance>> getPerformance() {
        return new ResponseEntity<>(userService.getPerformance(), HttpStatus.OK);
    }

    @ApiOperation("Зарегистрироваться")
    @PostMapping("/register")
    public void register(
            @Valid
            @RequestBody
            RegisterUser registerUser) {
        userService.registerUser(registerUser);
    }
}
