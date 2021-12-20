package io.papayankey.taskman.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class CustomResponse {

    private Object data;
    private Object error;
    private int status;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private CustomResponse() {}

    public static CustomResponse builder() {
        return new CustomResponse();
    }

    public <T> CustomResponse data(T data) {
        this.data = data;
        return this;
    }

    public <T> CustomResponse error(T error) {
        this.error = error;
        return this;
    }

    public <S extends HttpStatus> CustomResponse status(S status) {
       this.status = status.value();
       return this;
    }

    public CustomResponse message(String message) {
       this.message = message;
       return this;
    }

    public CustomResponse build() {
      this.setTimestamp(LocalDateTime.now());
      return this;
    }
}

