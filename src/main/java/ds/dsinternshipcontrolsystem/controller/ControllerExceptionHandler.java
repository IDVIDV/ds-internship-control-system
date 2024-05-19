package ds.dsinternshipcontrolsystem.controller;

import ds.dsinternshipcontrolsystem.exception.UserAlreadyJoinedInternshipException;
import ds.dsinternshipcontrolsystem.exception.UserAlreadyLeftInternshipException;
import ds.dsinternshipcontrolsystem.exception.ForkFailedException;
import ds.dsinternshipcontrolsystem.exception.InternshipRegistryClosedException;
import ds.dsinternshipcontrolsystem.exception.UnauthorizedException;
import ds.dsinternshipcontrolsystem.exception.UserAlreadyRegisteredException;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), headers, status);
    }

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

    @ExceptionHandler(UserAlreadyJoinedInternshipException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyJoinedInternshipException(UserAlreadyJoinedInternshipException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserAlreadyLeftInternshipException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyLeftInternshipException(UserAlreadyLeftInternshipException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAnyOtherException(RuntimeException e) {
        log.error("Unhandled exception caught", e);
        return e.getMessage();
    }

    @ExceptionHandler(GitLabApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGitlabApiException(GitLabApiException e) {
        log.error("Unhandled GitlabApi exception caught", e);
        return e.getMessage();
    }
}
