package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.service.TaskService;
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
@Validated
@RequiredArgsConstructor
@RequestMapping("internships/{internshipId}/lessons/{lessonId}")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    @ApiImplicitParam(
            name = "internshipId",
            dataTypeClass = Integer.class,
            defaultValue = "1",
            example = "1",
            paramType = "path"
    )
    public ResponseEntity<List<TaskDto>> getAllTasksByLessonId(
            @PathVariable(name = "lessonId") Integer lessonId) {
        return new ResponseEntity<>(taskService.getAllTasksByLessonId(lessonId), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    @ApiImplicitParam(
            name = "internshipId",
            dataTypeClass = Integer.class,
            defaultValue = "1",
            example = "1",
            paramType = "path"
    )
    public void addTaskToLesson(@RequestBody AddTask addTask,
                                @PathVariable(name = "lessonId") Integer lessonId) {
        addTask.setLessonId(lessonId);
        taskService.addTask(addTask);
    }
}
