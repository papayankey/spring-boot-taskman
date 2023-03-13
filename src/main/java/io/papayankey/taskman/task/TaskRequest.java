package io.papayankey.taskman.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TaskRequest {
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be empty or blank")
    private String description;
    @ValidTaskStatus
    private String status;
}
