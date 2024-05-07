package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class PublicController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterUser registerUser, Model model) {
        userService.registerUser(registerUser);
    }
}
