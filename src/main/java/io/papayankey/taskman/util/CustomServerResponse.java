package io.papayankey.taskman.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomServerResponse {
    private Object data;
    private Object error;
    private int status;
    private String title;
    private String detail;
    @Builder.Default
    private Date timestamp = new Date();
}

