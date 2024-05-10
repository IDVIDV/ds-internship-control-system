package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.TaskForkDto;
import ds.dsinternshipcontrolsystem.service.TaskForkService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/internships/{internshipId}/lessons/{lessonId}/tasks/{taskId}")
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "internshipId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id стажировки, которой принадлежит форк задания"
        ),
        @ApiImplicitParam(
                name = "lessonId",
                dataTypeClass = Integer.class,
                example = "1",
                paramType = "path",
                value = "Id занятия, которому принадлежит форк задания"
        ),
        @ApiImplicitParam(
                name = "taskId",
                dataTypeClass = Integer.class,
                defaultValue = "1",
                example = "1",
                paramType = "path",
                value = "Id заданиия, от которого был сделан форк"
        )
})
public class TaskForkController {
    private final TaskForkService taskForkService;

    @GetMapping("/task-forks")
    public ResponseEntity<List<TaskForkDto>> getAllTaskForksByTaskId(
            @Min(1)
            @PathVariable(name = "taskId")
            Integer taskId) {
        return new ResponseEntity<>(taskForkService.getAllTaskForksByTaskId(taskId), HttpStatus.OK);
    }

    @GetMapping("/task-forks/{taskForkId}")
    public ResponseEntity<TaskForkDto> getTaskForkById(
            @Min(1)
            @PathVariable(name = "taskForkId")
            Integer taskForkId) {
        return new ResponseEntity<>(taskForkService.getTaskForkById(taskForkId), HttpStatus.OK);
    }
    @PostMapping("/task-forks/{taskForkId}/accept")
    public void acceptTaskFork(@Min(1) @PathVariable(name = "taskForkId") Integer taskForkId) {
        taskForkService.acceptTaskFork(taskForkId);
    }
}
