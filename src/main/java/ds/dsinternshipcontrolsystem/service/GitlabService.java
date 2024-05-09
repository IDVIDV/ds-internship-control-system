package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Author;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.webhook.EventCommit;
import org.gitlab4j.api.webhook.EventRepository;
import org.gitlab4j.api.webhook.PushEvent;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitlabService {
    private final CommitRepository commitRepository;
    private final TaskForkRepository taskForkRepository;
    private final GitLabApi gitLabApi;

    public void handlePushEvent(PushEvent pushEvent) {
        pushEvent.getProject();

        EventRepository eventRepository = pushEvent.getRepository();

        if (eventRepository == null) {
            return;
            //TODO exception?
        }

        TaskFork taskFork = taskForkRepository.findByUrl(eventRepository.getHomepage());

        List<EventCommit> eventCommits = pushEvent.getCommits();
        List<Commit> commitsToSave = new ArrayList<>();

        for (EventCommit eventCommit : eventCommits) {
            Commit commit = new Commit();
            Author author = eventCommit.getAuthor();
            commit.setAuthor(author == null ? null : author.getName());
            commit.setCommitDate(Timestamp.from(eventCommit.getTimestamp().toInstant()));
            commit.setUrl(eventCommit.getUrl());
            commit.setTaskFork(taskFork);
            commitsToSave.add(commit);
        }

        commitRepository.saveAll(commitsToSave);
    }

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
