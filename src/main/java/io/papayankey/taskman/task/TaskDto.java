package io.papayankey.taskman.task;

import io.papayankey.taskman.task.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TaskDto {

    private Integer Id;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

}
