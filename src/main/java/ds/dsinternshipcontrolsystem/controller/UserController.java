package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/internship/{id}/register")
    public void signInInternship(@Min(1) @PathVariable("id") Integer internshipId) {
        userService.signInInternship(internshipId);
    }

    public void getMessages() {}

    public void getPerformance() {

    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleExceptions(RuntimeException e) {
        return e.getMessage();
    }
}
