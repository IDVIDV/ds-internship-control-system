package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.CommitDto;
import ds.dsinternshipcontrolsystem.entity.Commit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommitMapper {
    @Mapping(target = "taskForkId", source = "taskFork.id")
    @Mapping(target = "taskId", source = "taskFork.task.id")
    @Mapping(target = "taskName", source = "taskFork.task.taskName")
    @Mapping(target = "userId", source = "taskFork.user.id")
    @Mapping(target = "username", source = "taskFork.user.username")
    CommitDto toCommitDto(Commit commit);

    List<CommitDto> toCommitDtoList(List<Commit> commitList);
}
