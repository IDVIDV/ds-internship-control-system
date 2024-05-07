package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.service.GitlabService;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.webhook.PushEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GitlabController {
    private final GitlabService gitlabService;

    @Value("${GITLAB_PUSH_HOOK_SECRET_TOKEN:secret-token}")
    private String pushEventSecretToken;

    @PostMapping("/gitlab/push-event")
    public void handlePushEvent(@RequestBody PushEvent pushEvent) {
        if (!pushEvent.getRequestSecretToken().equals(pushEventSecretToken)) {
            return;
        }

    }
}
