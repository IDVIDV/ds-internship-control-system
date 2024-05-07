package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.mapper.TaskMapper;
import ds.dsinternshipcontrolsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public List<TaskDto> getAllTasksByLessonId(Integer lessonId) {
        return taskMapper.toTaskDtoList(taskRepository.findAllByLessonId(lessonId));
    }

    public void addTask(AddTask addTask) {
        Task task = taskMapper.toTask(addTask);
        taskRepository.save(task);
        //TODO сделать форки, если стажировка активна
    }
}
