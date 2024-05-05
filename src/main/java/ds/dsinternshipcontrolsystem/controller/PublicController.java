package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterUser registerUser, Model model) {

        if (!userService.registerUser(registerUser)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        }
    }
}
