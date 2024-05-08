package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddLesson;
import ds.dsinternshipcontrolsystem.dto.CommitDto;
import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.service.CommitService;
import ds.dsinternshipcontrolsystem.service.LessonService;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/internships/{internshipId}")
public class LessonController {
    private final LessonService lessonService;
    private final CommitService commitService;

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonDto>> getAllLessonsByInternshipId(
            @PathVariable(name = "internshipId") Integer internshipId) {
        return new ResponseEntity<>(lessonService.getAllLessonsByInternshipId(internshipId), HttpStatus.OK);
    }

    @GetMapping("/lessons/{lessonId}/unchecked-commits")
    @ApiImplicitParam(
            name = "internshipId",
            dataTypeClass = Integer.class,
            defaultValue = "1",
            example = "1",
            paramType = "path"
    )
    public ResponseEntity<List<CommitDto>> getUncheckedCommits(@PathVariable(name = "lessonId") Integer lessonId) {
        return new ResponseEntity<>(commitService.getAllLatestUncheckedCommits(lessonId), HttpStatus.OK);
    }

    @PostMapping("/lessons")
    public void addLessonToInternship(@RequestBody AddLesson addLesson,
                                      @PathVariable(name = "internshipId") Integer internshipId) {
        addLesson.setInternshipId(internshipId);
        lessonService.addLesson(addLesson);
    }
}
