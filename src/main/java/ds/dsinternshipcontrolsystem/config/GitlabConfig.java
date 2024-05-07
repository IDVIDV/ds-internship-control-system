package ds.dsinternshipcontrolsystem.config;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitlabConfig {
    @Value("${GITLAB_URL:http://localhost:9877}")
    private String gitlabUrl;
    @Value("${GITLAB_PASSWORD:ivakin_danya}")
    private String gitlabPassword;
    @Value("${GITLAB_PUSH_HOOK_URL:http://192.168.56.1:8080/gitlab/push-event}")
    private String pushEventUrl;
    @Value("${GITLAB_PUSH_HOOK_SECRET_TOKEN:secret-token}")
    private String pushEventSecretToken;
    private String gitlabUser = "root";

    @Bean
    public GitLabApi gitLabApi() throws GitLabApiException {
        GitLabApi gitLabApi = GitLabApi.oauth2Login(gitlabUrl, gitlabUser, gitlabPassword);

        boolean hookExists = gitLabApi.getSystemHooksApi()
                .getSystemHooks().stream()
                .anyMatch(hook -> hook.getUrl().equals(pushEventUrl));

        if (!hookExists) {
            gitLabApi.getSystemHooksApi().addSystemHook(pushEventUrl, pushEventSecretToken,
                    true, false, true);
        }

        return gitLabApi;
    }
}
