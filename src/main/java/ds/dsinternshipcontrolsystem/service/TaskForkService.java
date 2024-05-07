package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.projection.UserOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskForkService {
    private final GitlabService gitLabService;
    private final TaskForkRepository taskForkRepository;
    private final UserInternshipRepository userInternshipRepository;

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

    private void createForks(List<User> users, List<Task> tasks) {
        try {
            List<TaskFork> taskForks = new ArrayList<>();

            for (User user : users) {
                taskForks.addAll(gitLabService.createForks(tasks, user));
            }

            taskForkRepository.saveAll(taskForks);
        } catch (Exception e) {
            //TODO выслать всем админам сообщение
            System.out.println("abc");
        }
    }

    private List<User> getUsersInInternship(Integer internshipId) {
        return userInternshipRepository.findAllByInternshipId(internshipId)
                .stream().map(UserOnly::getUser).collect(Collectors.toList());
    }
}
