package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.dto.UserDto;
import ds.dsinternshipcontrolsystem.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterUser registerUser);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> userList);
}
