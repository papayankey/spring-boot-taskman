package io.papayankey.taskman.exception.advisor;

import io.papayankey.taskman.exception.TaskNotFoundException;
import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.util.CustomResponse;
import io.papayankey.taskman.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<CustomResponse> handleTaskNotFound(TaskNotFoundException exception, WebRequest request) {
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomResponse> handleUserExist(UserExistException exception, WebRequest request) {
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomResponse> handleBadCredential(BadCredentialsException exception, WebRequest request) {
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.BAD_REQUEST);
    }
}

