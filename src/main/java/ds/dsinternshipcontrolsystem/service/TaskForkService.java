package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.TaskForkDto;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.exception.ForkFailedException;
import ds.dsinternshipcontrolsystem.mapper.TaskForkMapper;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.projection.UserOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskForkService {
    private final GitlabService gitLabService;
    private final TaskForkRepository taskForkRepository;
    private final UserInternshipRepository userInternshipRepository;
    private final TaskForkMapper taskForkMapper;
    private final MessageService messageService;

    public List<TaskForkDto> getAllTaskForksByTaskId(Integer taskId) {
        return taskForkMapper.toTaskForkDtoList(taskForkRepository.findAllByTaskId(taskId));
    }

    public TaskForkDto getTaskForkById(Integer taskForkId) {
        TaskFork taskFork = taskForkRepository.findById(taskForkId).orElse(null);

        if (taskFork == null) {
            throw new EntityNotFoundException("TaskFork with given id does not exist");
        }

        return taskForkMapper.toTaskForkDto(taskFork);
    }

    public void acceptTaskFork(Integer taskForkId) {
        TaskFork taskFork = taskForkRepository.findById(taskForkId).orElse(null);

        if (taskFork == null) {
            throw new EntityNotFoundException("TaskFork with given id does not exist");
        }

        taskFork.setAccepted(true);

        taskForkRepository.save(taskFork);
    }

    public void createForksForTaskInOngoingInternship(Task task) {
        List<User> users = getUsersInInternship(task.getLesson()
                .getInternship().getId());

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        createForks(users, tasks);
    }

    public void createForksOnInternshipStart(Internship internship) {
        List<User> users = getUsersInInternship(internship.getId());
        List<Task> tasks = new ArrayList<>();

        for (Lesson lesson : internship.getLessons()) {
            tasks.addAll(lesson.getTasks());
        }

        createForks(users, tasks);
    }

    public void createForks(List<User> users, List<Task> tasks) {
        try {
            List<TaskFork> taskForks = new ArrayList<>();

            for (User user : users) {
                taskForks.addAll(gitLabService.createForks(tasks, user));
            }

            taskForkRepository.saveAll(taskForks);
        } catch (Exception e) {
            messageService.noticeAllAdminsOnForkFail(users, tasks, e.getMessage());
            throw new ForkFailedException(e.getMessage());
        }
    }

    private List<User> getUsersInInternship(Integer internshipId) {
        return userInternshipRepository.findAllByInternshipIdAndStatus(internshipId, UserInternshipStatus.JOINED)
                .stream().map(UserOnly::getUser).collect(Collectors.toList());
    }
}
