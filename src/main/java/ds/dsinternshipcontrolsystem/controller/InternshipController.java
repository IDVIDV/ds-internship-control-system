package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import ds.dsinternshipcontrolsystem.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleExceptions(ConstraintViolationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleExceptions(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(WrongInternshipStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleExceptions(WrongInternshipStatusException e) {
        return e.getMessage();
    }
}
