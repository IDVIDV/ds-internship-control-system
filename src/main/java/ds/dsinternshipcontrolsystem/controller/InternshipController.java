package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.dto.Report;
import ds.dsinternshipcontrolsystem.service.InternshipService;
import ds.dsinternshipcontrolsystem.service.ReportService;
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
public class InternshipController {
    private final InternshipService internshipService;
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<InternshipDto>> getAllInternships() {
        return new ResponseEntity<>(internshipService.getAllInternships(), HttpStatus.OK);
    }

    @GetMapping("/{internshipId}")
    public ResponseEntity<InternshipDto> getInternshipById(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        return new ResponseEntity<>(internshipService.getInternshipById(internshipId), HttpStatus.OK);
    }

    @PostMapping
    public void addInternship(@Valid @RequestBody AddInternship addInternship) {
        internshipService.addInternship(addInternship);
    }

    @PostMapping("/{internshipId}/end-registry")
    public void endRegistry(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.endRegistry(internshipId);
    }

    @PostMapping("/{internshipId}/start")
    public void startInternship(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.startInternship(internshipId);
    }

    @PostMapping("/{internshipId}/end")
    public void endInternship(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        internshipService.endInternship(internshipId);
    }

    @GetMapping("/{internshipId}/report")
    public ResponseEntity<Report> getReport(
            @Min(1)
            @PathVariable(name = "internshipId")
            Integer internshipId) {
        return new ResponseEntity<>(reportService.getReport(internshipId), HttpStatus.OK);
    }
}
