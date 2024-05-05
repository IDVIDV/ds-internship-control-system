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

//    @PostMapping("/test-git-repupdate")
//    public void testRepUpdate(@RequestBody RepositoryChange str) {
//        System.out.println("REPUPDATE");
//        System.out.println(str);
//        System.out.println();
//        System.out.println();
////        System.out.println(pushEvent);
////        System.out.println(repChangeEvent);
//    }
//
//    @PostMapping("/test-git-push")
//    public void testPush(@RequestBody PushEvent str){
//        System.out.println("PUSH");
//        System.out.println(str);
//        System.out.println();
//        System.out.println();
////        System.out.println(repChangeEvent);
//    }

    @PostMapping("/gitlab/push-event")
    public String onPushEvent(@RequestBody PushEvent pushEvent) {
        if (!pushEvent.getRequestSecretToken().equals(pushEventSecretToken)) {
            return "FAIL";
        }

        return "OK";
    }
}
