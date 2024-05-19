package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.mapper.TaskMapper;
import ds.dsinternshipcontrolsystem.repository.LessonRepository;
import ds.dsinternshipcontrolsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final LessonRepository lessonRepository;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskForkService taskForkService;

    public List<TaskDto> getAllTasksByLessonId(Integer lessonId) {
        return taskMapper.toTaskDtoList(taskRepository.findAllByLessonId(lessonId));
    }

    public TaskDto getTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            throw new EntityNotFoundException(String.format("Task with id %d does not exist", taskId));
        }

        return taskMapper.toTaskDto(task);
    }

    public void addTask(AddTask addTask) {
        Lesson lesson = lessonRepository.findById(addTask.getLessonId()).orElse(null);

        if (lesson == null) {
            throw new EntityNotFoundException(String.format("Lesson with id %d does not exist",
                    addTask.getLessonId()));
        }

        Task task = taskMapper.toTask(addTask);
        task.setLesson(lesson);
        taskRepository.save(task);

        if (lesson.getInternship().getStatus().equals(InternshipStatus.IN_PROGRESS)) {
            taskForkService.createForksForTaskInOngoingInternship(task);
        }
    }
}
