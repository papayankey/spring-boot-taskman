package io.papayankey.taskman.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ResponseHandler {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private Object error;
    private Object data;

    private ResponseHandler() {
    }

    public static ResponseHandler builder() {
        return new ResponseHandler();
    }

    public ResponseHandler status(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ResponseHandler message(String message) {
        this.message = message;
        return this;
    }

    public <T> ResponseHandler error(T error) {
        this.error = error;
        return this;
    }

    public <T> ResponseHandler data(T data) {
        this.data = data;
        return this;
    }

    public ResponseEntity<CustomResponse> build() {
        CustomResponse customResponse = new CustomResponse();
        customResponse.put("data", getData());
        customResponse.put("error", getError());
        customResponse.put("message", getMessage());
        customResponse.put("status", getStatus());
        customResponse.put("timestamp", getTimestamp());

        return new ResponseEntity<>(customResponse, HttpStatus.valueOf(getStatus()));
    }
}
