package io.papayankey.taskman.exception.advisor;

import io.papayankey.taskman.exception.TaskNotFoundException;
import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.util.CustomServerResponse;
import io.papayankey.taskman.util.ResponseHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<CustomServerResponse> onTaskNotFoundException(TaskNotFoundException exception, WebRequest request) {
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title(HttpStatus.NOT_FOUND.name())
                .detail(exception.getMessage())
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomServerResponse> onUserExistException(UserExistException exception, WebRequest request) {
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .title(HttpStatus.CONFLICT.name())
                .detail(exception.getMessage())
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomServerResponse> onBadCredentialException(BadCredentialsException exception, WebRequest request) {
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.name())
                .detail(exception.getMessage())
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        CustomServerResponse customResponse = CustomServerResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.name())
                .error(errors)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomServerResponse> onConstraintsViolationExceptions(ConstraintViolationException exception, WebRequest request) {
        Stream<String> constraintViolationMessages = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.name())
                .error(constraintViolationMessages)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.BAD_REQUEST);
    }
}
