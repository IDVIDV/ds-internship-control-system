package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.archive.ArchiveInternship;
import ds.dsinternshipcontrolsystem.mapper.InternshipMapper;
import ds.dsinternshipcontrolsystem.mapper.TaskForkMapper;
import ds.dsinternshipcontrolsystem.mapper.UserInternshipMapper;
import ds.dsinternshipcontrolsystem.mapper.UserMapper;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.archive.ArchiveInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.archive.ArchivePerformanceRepository;
import ds.dsinternshipcontrolsystem.repository.archive.ArchiveUserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.archive.ArchiveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchiveService {
    private final ArchiveUserRepository archiveUserRepository;
    private final ArchivePerformanceRepository archivePerformanceRepository;
    private final ArchiveUserInternshipRepository archiveUserInternshipRepository;
    private final ArchiveInternshipRepository archiveInternshipRepository;
    private final UserInternshipRepository userInternshipRepository;
    private final TaskForkRepository taskForkRepository;
    private final UserMapper userMapper;
    private final UserInternshipMapper userInternshipMapper;
    private final TaskForkMapper taskForkMapper;
    private final InternshipMapper internshipMapper;

    public void archiveUsersInInternship(List<User> users, Internship internship) {
        ArchiveInternship archiveInternship = internshipMapper.toArchiveInternship(internship);

        archiveInternshipRepository.save(archiveInternship);
        archiveUserRepository.saveAll(userMapper.toArchiveUserList(users));

        List<Integer> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        archiveUserInternshipRepository.saveAll(
                userInternshipRepository.findAllByInternshipIdAndUserIdIn(
                        internship.getId(),
                        userIds
                )
                .stream()
                .map(userInternshipMapper::toArchiveUserInternship)
                .collect(Collectors.toList())
        );

        archivePerformanceRepository.saveAll(
                taskForkRepository.findAllByUserIdInAndTaskLessonInternshipId(userIds, internship.getId())
                        .stream()
                        .map(taskForkMapper::toArchivePerformance)
                        .map(performance -> {
                            performance.setInternship(archiveInternship);
                            return performance;}
                        ).collect(Collectors.toList())
        );
    }
}
