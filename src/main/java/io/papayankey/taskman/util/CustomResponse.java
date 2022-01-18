package io.papayankey.taskman.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Builder
@Data
public class CustomResponse {

    private Object data;
    private Object error;
    private int status;
    private String message;

    @Builder.Default
    private Date timestamp = new Date();

}

