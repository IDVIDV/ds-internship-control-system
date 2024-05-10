package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddComment;
import ds.dsinternshipcontrolsystem.dto.CommentDto;
import ds.dsinternshipcontrolsystem.entity.Comment;
import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.mapper.CommentMapper;
import ds.dsinternshipcontrolsystem.repository.CommentRepository;
import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MessageService messageService;
    private final CommitRepository commitRepository;

    public void addComment(AddComment addComment) {
        Comment comment = commentMapper.toComment(addComment);
        Commit commit = commitRepository.findById(comment.getCommit().getId()).orElse(null);
        comment.setCommit(commit);
        comment.setUser(commit.getTaskFork().getUser());
        commentRepository.save(comment);
        messageService.noteStudentOnCommitCheck(comment);
    }

    public List<CommentDto> getAllCommentsByCommitId(Integer commitId) {
        return commentMapper.toCommentDtoList(commentRepository.findAllByCommitId(commitId));
    }
}
