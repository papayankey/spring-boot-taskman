package io.papayankey.taskman.task;

import io.papayankey.taskman.task.exceptions.TaskNotFoundException;
import io.papayankey.taskman.util.CustomResponse;
import io.papayankey.taskman.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TaskControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<CustomResponse> handleTaskNotFoundException(TaskNotFoundException exception, WebRequest request) {
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.NOT_FOUND);
    }
}
