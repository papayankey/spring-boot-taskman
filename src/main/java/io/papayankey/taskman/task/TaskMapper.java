package io.papayankey.taskman.task;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface TaskMapper {
    @Mapping(target = "status", expression = "java(TaskStatus.valueOf(taskRequest.getStatus().toUpperCase()))")
    TaskEntity toTaskEntity(TaskRequest taskRequest);

    @Mapping(target = "status", expression = "java(taskEntity.getStatus().name().toLowerCase())")
    TaskResponse toTaskResponse(TaskEntity taskEntity);

    List<TaskResponse> toTaskReponseList(List<TaskEntity> taskEntities);
}
