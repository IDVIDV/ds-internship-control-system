package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.dto.UserDto;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.archive.ArchiveUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterUser registerUser);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> userList);

    @Mapping(target = "userInternships", ignore = true)
    @Mapping(target = "performances", ignore = true)
    ArchiveUser toArchiveUser(User user);

    List<ArchiveUser> toArchiveUserList(List<User> userList);
}
