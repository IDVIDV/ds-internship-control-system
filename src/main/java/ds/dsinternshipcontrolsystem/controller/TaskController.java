package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddTask;
import ds.dsinternshipcontrolsystem.dto.TaskDto;
import ds.dsinternshipcontrolsystem.dto.TaskItem;
import ds.dsinternshipcontrolsystem.service.TaskService;
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
@Validated
@RequiredArgsConstructor
@RequestMapping("/internships/{internshipId}/lessons/{lessonId}")
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
@Api(tags = {"Task Controller"}, description = "Обрабатывает запросы, связанные с заданиями")
public class TaskController {
    private final TaskService taskService;

    @ApiOperation("Получить все задания занятия")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskItem>> getAllTasksByLessonId(
            @Min(1)
            @PathVariable(name = "lessonId")
            Integer lessonId) {
        return new ResponseEntity<>(taskService.getAllTasksByLessonId(lessonId), HttpStatus.OK);
    }

    @ApiOperation("Получить задание")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(
            @ApiParam(value = "Id задания", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "taskId")
            Integer taskId) {
        return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);
    }

    @ApiOperation("Добавить задание")
    @PostMapping("/tasks")
    public void addTaskToLesson(
            @Valid
            @RequestBody AddTask addTask,

            @Min(1)
            @PathVariable(name = "lessonId")
            Integer lessonId) {
        addTask.setLessonId(lessonId);
        taskService.addTask(addTask);
    }

}
