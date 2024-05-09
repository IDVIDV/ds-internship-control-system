package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddComment;
import ds.dsinternshipcontrolsystem.dto.CommentDto;
import ds.dsinternshipcontrolsystem.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "user",
            expression = "java(new ds.dsinternshipcontrolsystem.entity." +
                    "User(addComment.getUserId(), null, null, null, null, null, " +
                    "null, null, null, null, null, null, null, null, null))"
    )
    @Mapping(target = "commit",
            expression = "java(new ds.dsinternshipcontrolsystem.entity." +
                    "Commit(addComment.getCommitId(), null, null, null, null, null))"
    )
    Comment toComment(AddComment addComment);

    @Mapping(target = "commitUrl", source = "commit.url")
    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDtoList(List<Comment> commentList);
}
