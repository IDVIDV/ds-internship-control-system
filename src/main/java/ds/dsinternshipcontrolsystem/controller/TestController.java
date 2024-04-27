package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.service.GitlabService;
import ds.dsinternshipcontrolsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final GitlabService gitlabService;
    private final UserService userService;

    @Autowired
    public TestController(UserService userService, GitlabService gitlabService) {
        this.userService = userService;
        this.gitlabService = gitlabService;
    }

    @ApiOperation(value = "admin test")
    @GetMapping("/admin")
    private String adminTest(Model model) {
        return gitlabService.test();
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
