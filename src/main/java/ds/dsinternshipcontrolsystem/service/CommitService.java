package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.CommitDto;
import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.mapper.CommitMapper;
import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommitService {
    private final CommitRepository commitRepository;
    private final CommitMapper commitMapper;

    public CommitDto getUncheckedCommitById(Integer commitId) {
        Commit commit = commitRepository.findByIdAndTaskForkAccepted(commitId, false);

        if (commit == null) {
            throw new EntityNotFoundException("Commit with given id does not exist or was accepted");
        }

        return commitMapper.toCommitDto(commit);
    }

    public List<CommitDto> getAllLatestUncheckedCommits(Integer lessonId) {
        return commitMapper.toCommitDtoList(commitRepository
                .findAllLatestUncheckedCommitsByLessonId(lessonId));
    }
}
