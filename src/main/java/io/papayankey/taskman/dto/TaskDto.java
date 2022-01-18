package io.papayankey.taskman.dto;

import io.papayankey.taskman.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
public class TaskDto {

    private Integer Id;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

}
