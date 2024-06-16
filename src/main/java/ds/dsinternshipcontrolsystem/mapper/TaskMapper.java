package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.dto.TaskItem;
import ds.dsinternshipcontrolsystem.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskForkMapper.class)
public interface TaskMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "taskForkItemList", source = "taskForks")
    TaskDto toTaskDto(Task task);

    @Mapping(target = "lessonId", source = "lesson.id")
    TaskItem toTaskItem(Task task);

    List<TaskItem> toTaskItemList(List<Task> taskList);

    @Mapping(target = "lesson",
            expression = "java(new ds.dsinternshipcontrolsystem.entity." +
                    "Lesson(addTask.getLessonId(), null, null, null, null))")
    Task toTask(AddTask addTask);
}
