package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitlabService {
    private final GitLabApi gitLabApi;

    public List<TaskFork> createForks(List<Task> tasks, User user) throws GitLabApiException {
        org.gitlab4j.api.models.User gitlabUser = gitLabApi.getUserApi().getUser(user.getUsername());

        if (gitlabUser == null) {
            gitlabUser = registerUser(user);
        }

        List<TaskFork> taskForks = new ArrayList<>();

        for (Task task : tasks) {
            Project fork = gitLabApi.getProjectApi()
                    .forkProject(task.getPath(), gitlabUser.getNamespaceId());
            TaskFork taskFork = new TaskFork(null, task, user,
                    false, fork.getWebUrl(), null);
            taskForks.add(taskFork);
        }

        return taskForks;
    }

    public org.gitlab4j.api.models.User registerUser(User user) throws GitLabApiException {
        org.gitlab4j.api.models.User gitlabUser = gitLabApi.getUserApi().getUser(user.getUsername());

        if (gitlabUser != null) {
            return gitlabUser; //Пользователь уже имеет аккаунт в gitlab
        }

        gitlabUser = new org.gitlab4j.api.models.User()
                .withSkipConfirmation(true)
                .withEmail(user.getMail())
                .withUsername(user.getUsername())
                .withName(user.getFullName());

        return gitLabApi.getUserApi().createUser(gitlabUser, user.getUsername(), true);
    }
}
