package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddComment;
import ds.dsinternshipcontrolsystem.dto.CommitDto;
import ds.dsinternshipcontrolsystem.service.CommentService;
import ds.dsinternshipcontrolsystem.service.CommitService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/internships/{internshipId}/lessons/{lessonId}")
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "internshipId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id стажировки, которой принадлежит коммит"
        ),
        @ApiImplicitParam(
                name = "lessonId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id занятия, которому принадлежит коммит"
        )
})
public class UncheckedCommitController {
    private final CommitService commitService;
    private final CommentService commentService;

    @GetMapping("/unchecked-commits")
    public ResponseEntity<List<CommitDto>> getAllUncheckedCommitsByLessonId(
            @PathVariable(name = "lessonId") Integer lessonId) {
        return new ResponseEntity<>(commitService.getAllLatestUncheckedCommits(lessonId), HttpStatus.OK);
    }

    @GetMapping("/unchecked-commits/{commitId}")
    public ResponseEntity<CommitDto> getUncheckedCommitById(
            @PathVariable(name = "commitId") Integer commitId) {
        return new ResponseEntity<>(commitService.getUncheckedCommitById(commitId), HttpStatus.OK);
    }

    @PostMapping("/unchecked-commits/{commitId}/comments")
    public void addCommentToCommit(@RequestBody AddComment addComment,
                                   @PathVariable(name = "commitId") Integer commitId) {
        addComment.setCommitId(commitId);
        commentService.addComment(addComment);
    }
}
