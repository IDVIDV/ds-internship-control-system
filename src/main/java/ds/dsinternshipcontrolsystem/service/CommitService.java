package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.CommitDto;
import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.mapper.CommitMapper;
import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.models.Author;
import org.gitlab4j.api.webhook.EventCommit;
import org.gitlab4j.api.webhook.EventRepository;
import org.gitlab4j.api.webhook.PushEvent;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommitService {
    private final TaskForkRepository taskForkRepository;
    private final CommitRepository commitRepository;
    private final CommitMapper commitMapper;

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

    public List<CommitDto> getAllLatestUncheckedCommits(Integer lessonId) {
        return commitMapper.toCommitDtoList(commitRepository
                .findAllLatestUncheckedCommitsByLessonId(lessonId));
    }
}
