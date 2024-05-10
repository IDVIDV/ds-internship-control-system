package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.dto.Report;
import ds.dsinternshipcontrolsystem.service.InternshipService;
import ds.dsinternshipcontrolsystem.service.ReportService;
import io.swagger.annotations.Api;
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
@RequestMapping("/internships")
@Api(tags = {"Internship Controller"}, description = "Обрабатывает запросы, связанные с стажировками")
public class InternshipController {
    private final InternshipService internshipService;
    private final ReportService reportService;

    @ApiOperation("Получить все стажировки")
    @GetMapping
    public ResponseEntity<List<InternshipDto>> getAllInternships() {
        return new ResponseEntity<>(internshipService.getAllInternships(), HttpStatus.OK);
    }

    @ApiOperation("Получить стажировку")
    @GetMapping("/{internshipId}")
    public ResponseEntity<InternshipDto> getInternshipById(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        return new ResponseEntity<>(internshipService.getInternshipById(internshipId), HttpStatus.OK);
    }

    @ApiOperation("Добавить новую стажировку")
    @PostMapping
    public void addInternship(@Valid @RequestBody AddInternship addInternship) {
        internshipService.addInternship(addInternship);
    }

    @ApiOperation("Закрыть регистрацию на стажировку")
    @PostMapping("/{internshipId}/end-registry")
    public void endRegistry(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.endRegistry(internshipId);
    }

    @ApiOperation("Начать стажировку")
    @PostMapping("/{internshipId}/start")
    public void startInternship(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.startInternship(internshipId);
    }

    @ApiOperation("Завершить стажировку")
    @PostMapping("/{internshipId}/end")
    public void endInternship(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.endInternship(internshipId);
    }

    @ApiOperation("Получить отчет по стажировке")
    @GetMapping("/{internshipId}/report")
    public ResponseEntity<Report> getReport(
            @ApiParam(value = "Id стажировки", example = "1", type = "path")
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        return new ResponseEntity<>(reportService.getReport(internshipId), HttpStatus.OK);
    }
}
