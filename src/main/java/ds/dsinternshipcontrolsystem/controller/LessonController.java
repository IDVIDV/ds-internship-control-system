package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddLesson;
import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/internships/{internshipId}")
@ApiImplicitParams(
        @ApiImplicitParam(
                name = "internshipId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id стажировки, которой принадлежит занятие"
        )
)
@Api(tags = {"Lesson Controller"}, description = "Обрабатывает запросы, связанные с занятиями")
public class LessonController {
    private final LessonService lessonService;

    @ApiOperation("Получить все занятия стажировки")
    @GetMapping("/lessons")
    public ResponseEntity<List<LessonDto>> getAllLessonsByInternshipId(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        return new ResponseEntity<>(lessonService.getAllLessonsByInternshipId(internshipId), HttpStatus.OK);
    }

    @ApiOperation("Получить занятие")
    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(
            @ApiParam(value = "Id занятия", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "lessonId")
            Integer lessonId) {
        return new ResponseEntity<>(lessonService.getLessonById(lessonId), HttpStatus.OK);
    }

    @ApiOperation("Добавить новое занятие")
    @PostMapping("/lessons")
    public void addLessonToInternship(
            @Valid
            @RequestBody
            AddLesson addLesson,

            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        addLesson.setInternshipId(internshipId);
        lessonService.addLesson(addLesson);
    }
}
