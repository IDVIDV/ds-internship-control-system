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
    private String gitlabUser = "root";
    @Bean
    public GitLabApi gitLabApi() throws GitLabApiException {
        return GitLabApi.oauth2Login(gitlabUrl, gitlabUser, gitlabPassword);
    }
}
