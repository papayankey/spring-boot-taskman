package io.papayankey.taskman.task;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskResponse {
    @NotNull
    private int id;
    @NotNull
    private String description;
    @NotNull
    private String status;
}
