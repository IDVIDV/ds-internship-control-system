package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.archive.ArchiveUserInternship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, InternshipMapper.class})
public interface UserInternshipMapper {
    ArchiveUserInternship toArchiveUserInternship(UserInternship userInternship);
}
