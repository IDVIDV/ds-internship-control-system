package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "admin test")
    @GetMapping("/admin")
    private String adminTest(Model model) {
        return "1";
    }

    @ApiOperation(value = "user test")
    @GetMapping("/user/1")
    private String userTest(Model model) {
        return "2";
    }

    @ApiOperation(value = "public test")
    @GetMapping("/public")
    private String publicTest(Model model) {
        return "3";
    }

    @PostMapping("/registration")
    public String addUser(@RequestBody User user, Model model) {

        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        }

        return "OK";
    }
}
