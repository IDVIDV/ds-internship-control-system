package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.MessageDto;
import ds.dsinternshipcontrolsystem.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "receiverUsername", source = "receiver.username")
    MessageDto toMessageDto(Message message);

    List<MessageDto> toMessageDtoList(List<Message> messageList);
}
