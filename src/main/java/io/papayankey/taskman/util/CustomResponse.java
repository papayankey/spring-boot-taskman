package io.papayankey.taskman.util;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomResponse {
    private Object data;
    private Object error;
    private int status;
    private String message;

    @Builder.Default
    private Date timestamp = new Date();
}

