package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.MessageDto;
import ds.dsinternshipcontrolsystem.dto.Performance;
import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.service.UserService;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/internship/{id}/register")
    public void signInInternship(
            @Min(1)
            @ApiParam(name = "idTest", value = "Тест", type = "path")
            @PathVariable("id")
            Integer internshipId) {
        userService.signInInternship(internshipId);
    }

    @GetMapping("/user/messages")
    public ResponseEntity<List<MessageDto>> getMessages() {
        return new ResponseEntity<>(userService.getMessages(), HttpStatus.OK);
    }

    @GetMapping("/user/performance")
    public ResponseEntity<List<Performance>> getPerformance() {
        return new ResponseEntity<>(userService.getPerformance(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterUser registerUser) {
        userService.registerUser(registerUser);
    }
}
