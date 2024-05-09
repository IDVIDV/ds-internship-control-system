package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.Performance;
import ds.dsinternshipcontrolsystem.dto.TaskForkDto;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommitMapper.class)
public interface TaskForkMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    TaskForkDto toTaskForkDto(TaskFork taskFork);

    List<TaskForkDto> toTaskForkDtoList(List<TaskFork> taskForkList);

    Performance toPerformance(TaskFork taskFork);

    List<Performance> toPerformanceList(List<TaskFork> taskForkList);
}
