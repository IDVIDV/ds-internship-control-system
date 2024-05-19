package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Author;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.webhook.EventCommit;
import org.gitlab4j.api.webhook.EventRepository;
import org.gitlab4j.api.webhook.PushEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabService {
    private final CommitRepository commitRepository;
    private final TaskForkRepository taskForkRepository;
    private final GitLabApi gitLabApi;

    @Value("${GITLAB_USER_PASSWORD:d7NTEU4RjG3bwWua}")
    private String gitlabDefaultUserPassword;

    public void handlePushEvent(PushEvent pushEvent) {
        EventRepository eventRepository = pushEvent.getRepository();

        if (eventRepository == null) {
            log.info("Push event {} rejected because of null eventRepository", pushEvent);
            return;
        }

        TaskFork taskFork = taskForkRepository.findByUrl(eventRepository.getHomepage());

        if (taskFork == null) {
            log.info("Push event {} rejected because of unregistered fork", pushEvent);
            return;
        }

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

        log.info("Push event handled: {}; saved commits: {}", pushEvent, commitsToSave);
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

        log.info("Forks created for user {} of tasks {}", user, tasks);

        return taskForks;
    }

    public org.gitlab4j.api.models.User registerUser(User user) throws GitLabApiException {
        org.gitlab4j.api.models.User gitlabUser = gitLabApi.getUserApi().getUser(user.getUsername());

        if (gitlabUser != null) {
            return gitlabUser;
        }

        gitlabUser = new org.gitlab4j.api.models.User()
                .withSkipConfirmation(true)
                .withEmail(user.getMail())
                .withUsername(user.getUsername())
                .withName(user.getFullName());

        gitlabUser = gitLabApi.getUserApi().createUser(gitlabUser,
                gitlabDefaultUserPassword, false);

        log.info("New user registered in gitlab: {}; created account info: {}", user, gitlabUser);

        return gitlabUser;
    }
}
