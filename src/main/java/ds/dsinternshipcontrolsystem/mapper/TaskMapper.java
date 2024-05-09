package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskForkMapper.class)
public interface TaskMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    TaskDto toTaskDto(Task task);

    List<TaskDto> toTaskDtoList(List<Task> taskList);
    @Mapping(target = "lesson",
            expression = "java(new ds.dsinternshipcontrolsystem.entity." +
                    "Lesson(addTask.getLessonId(), null, null, null, null))")
    Task toTask(AddTask addTask);
}
