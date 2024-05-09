package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddComment;
import ds.dsinternshipcontrolsystem.dto.CommentDto;
import ds.dsinternshipcontrolsystem.entity.Comment;
import ds.dsinternshipcontrolsystem.mapper.CommentMapper;
import ds.dsinternshipcontrolsystem.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public void addComment(AddComment addComment) {
        Comment comment = commentMapper.toComment(addComment);
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsByUserId(Integer userId) {
        return commentMapper.toCommentDtoList(commentRepository.findAllByUserId(userId));
    }
}
