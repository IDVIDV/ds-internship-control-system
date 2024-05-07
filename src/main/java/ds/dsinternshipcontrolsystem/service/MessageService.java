package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.MessageDto;
import ds.dsinternshipcontrolsystem.mapper.MessageMapper;
import ds.dsinternshipcontrolsystem.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public List<MessageDto> getMessagesByReceiverId(Integer receiverId) {
        return messageMapper.toMessageDtoList(messageRepository.findAllByReceiverId(receiverId));
    }
}
