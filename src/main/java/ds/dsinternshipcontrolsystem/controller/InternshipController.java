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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class InternshipController {
    private final InternshipService internshipService;

    @GetMapping("/internships")
    public ResponseEntity<List<InternshipDto>> getInternships() {
        return new ResponseEntity<>(internshipService.getAllInternships(), HttpStatus.OK);
    }

    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PostMapping("/internships")
    public void addInternship(@RequestBody AddInternship addInternship) {
        internshipService.addInternship(addInternship);
    }

    @PostMapping("/internships/{id}/end-registry")
    public void endRegistry(@Min(1) @PathVariable(name = "id") Integer internshipId) {
        internshipService.endRegistry(internshipId);
    }

    @PostMapping("/internships/{id}/start")
    public void startInternship(@Min(1) @PathVariable(name = "id") Integer internshipId) {
        internshipService.startInternship(internshipId);
    }

    @PostMapping("/internships/{id}/end")
    public void endInternship(@Min(1) @PathVariable(name = "id") Integer internshipId) {
        internshipService.endInternship(internshipId);
    }
}
