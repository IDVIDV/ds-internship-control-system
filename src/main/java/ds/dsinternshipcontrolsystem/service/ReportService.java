package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.Report;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.mapper.TaskMapper;
import ds.dsinternshipcontrolsystem.mapper.UserMapper;
import ds.dsinternshipcontrolsystem.repository.TaskRepository;
import ds.dsinternshipcontrolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    public Report getReport(Integer internshipId) {
        List<Task> tasks = taskRepository.findAllByLessonInternshipId(internshipId);
        List<User> users = userRepository.findAllByUserInternshipsInternshipIdAndUserInternshipsStatus(
                internshipId,
                UserInternshipStatus.JOINED
        );

        return new Report(userMapper.toUserDtoList(users), taskMapper.toTaskItemList(tasks));
    }
}
