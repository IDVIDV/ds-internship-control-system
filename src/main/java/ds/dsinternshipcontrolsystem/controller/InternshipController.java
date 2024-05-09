package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.service.InternshipService;
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

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/internships")
public class InternshipController {
    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<List<InternshipDto>> getAllInternships() {
        return new ResponseEntity<>(internshipService.getAllInternships(), HttpStatus.OK);
    }

    @GetMapping("/{internshipId}")
    public ResponseEntity<InternshipDto> getInternshipById(
            @PathVariable(name = "internshipId") Integer internshipId) {
        return new ResponseEntity<>(internshipService.getInternshipById(internshipId), HttpStatus.OK);
    }

    @PostMapping
    public void addInternship(@RequestBody AddInternship addInternship) {
        internshipService.addInternship(addInternship);
    }

    @PostMapping("/{internshipId}/end-registry")
    public void endRegistry(@Min(1) @PathVariable(name = "internshipId") Integer internshipId) {
        internshipService.endRegistry(internshipId);
    }

    @PostMapping("/{internshipId}/start")
    public void startInternship(@Min(1) @PathVariable(name = "internshipId") Integer internshipId) {
        internshipService.startInternship(internshipId);
    }

    @PostMapping("/{internshipId}/end")
    public void endInternship(@Min(1) @PathVariable(name = "internshipId") Integer internshipId) {
        internshipService.endInternship(internshipId);
    }

    @GetMapping("/{internshipId}/report")
    public ResponseEntity getReport(@PathVariable(name = "internshipId") Integer internshipId) {
        return null;
    }
}
