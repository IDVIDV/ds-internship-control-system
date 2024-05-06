package ds.dsinternshipcontrolsystem.controller;

import org.gitlab4j.api.webhook.PushEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitlabController {
    @Value("${GITLAB_PUSH_HOOK_SECRET_TOKEN:secret-token}")
    private String pushEventSecretToken;

    @PostMapping("/gitlab/push-event")
    public String onPushEvent(@RequestBody PushEvent pushEvent) {
        if (!pushEvent.getRequestSecretToken().equals(pushEventSecretToken)) {
            return "FAIL";
        }

        return "OK";
    }
}
