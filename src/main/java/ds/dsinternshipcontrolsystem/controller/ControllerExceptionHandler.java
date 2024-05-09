package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.exception.ForkFailedException;
import ds.dsinternshipcontrolsystem.exception.InternshipRegistryClosedException;
import ds.dsinternshipcontrolsystem.exception.UserAlreadyRegisteredException;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(WrongInternshipStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWrongInternshipStatusException(WrongInternshipStatusException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InternshipRegistryClosedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInternshipRegistryClosedException(InternshipRegistryClosedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ForkFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleForkFailedExceptionException(ForkFailedException e) {
        return e.getMessage();
    }
}
