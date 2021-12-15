package io.papayankey.taskman.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    private ResponseHandler() {}

    public static <T, S extends HttpStatus> ResponseEntity<T> create(T responseBody, S responseStatus) {
        return new ResponseEntity<>(responseBody, responseStatus);
    }
}
