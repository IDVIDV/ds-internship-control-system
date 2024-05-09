package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.service.TaskService;
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
@Validated
@RequiredArgsConstructor
@RequestMapping("internships/{internshipId}/lessons/{lessonId}")
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "internshipId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id стажировки, которой принадлежит задание"
        ),
        @ApiImplicitParam(
                name = "lessonId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id занятия, которому принадлежат задание"
        )
})
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasksByLessonId(
            @PathVariable(name = "lessonId") Integer lessonId) {
        return new ResponseEntity<>(taskService.getAllTasksByLessonId(lessonId), HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(
            @PathVariable(name = "taskId") Integer taskId) {
        return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public void addTaskToLesson(@RequestBody AddTask addTask,
                                @PathVariable(name = "lessonId") Integer lessonId) {
        addTask.setLessonId(lessonId);
        taskService.addTask(addTask);
    }

}
