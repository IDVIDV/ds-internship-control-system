package ds.dsinternshipcontrolsystem.service;

import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitlabService {
    private final GitLabApi gitLabApi;

    @Autowired
    public GitlabService(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    public String test() {
        return gitLabApi.getGitLabServerUrl();
    }
}
