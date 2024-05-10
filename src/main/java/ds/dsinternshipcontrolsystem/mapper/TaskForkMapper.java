package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.Performance;
import ds.dsinternshipcontrolsystem.dto.TaskForkDto;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.archive.ArchivePerformance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommitMapper.class, UserMapper.class})
public interface TaskForkMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "commitDtoList", source = "commits")
    TaskForkDto toTaskForkDto(TaskFork taskFork);

    List<TaskForkDto> toTaskForkDtoList(List<TaskFork> taskForkList);

    Performance toPerformance(TaskFork taskFork);

    List<Performance> toPerformanceList(List<TaskFork> taskForkList);

    @Mapping(target = "internship", ignore = true)
    @Mapping(target = "forkUrl", source = "url")
    ArchivePerformance toArchivePerformance(TaskFork taskFork);
}
