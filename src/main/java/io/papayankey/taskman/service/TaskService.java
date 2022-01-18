package io.papayankey.taskman.service;

import io.papayankey.taskman.dto.TaskDto;
import io.papayankey.taskman.exception.TaskNotFoundException;
import io.papayankey.taskman.model.TaskEntity;
import io.papayankey.taskman.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDto> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return toListDto(taskEntities);
    }

    public TaskDto createTask(TaskDto taskDto) {
        TaskEntity taskEntity = taskRepository.save(toEntity(taskDto));
        return toDto(taskEntity);
    }

    public TaskDto getTask(int id) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return toDto(optionalTask.get());
        }
        throw new TaskNotFoundException(id);
    }

    public List<TaskDto> getTasksByStatus(String status) {
        List<TaskEntity> taskEntities = taskRepository.findByStatus(status);
        return toListDto(taskEntities);
    }

    public TaskDto deleteTask(int id) {
        TaskDto taskDto = getTask(id);
        taskRepository.deleteById(id);
        return taskDto;
    }

    public void updateTask(int id, TaskDto taskDto) {
        taskRepository.findByIdAndUpdate(id, taskDto.getDescription(), taskDto.getStatus());
    }

    private List<TaskDto> toListDto(List<TaskEntity> taskEntities) {
        return taskEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private TaskEntity toEntity(TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setStatus(taskDto.getStatus());
        return taskEntity;
    }

    private TaskDto toDto(TaskEntity taskEntity) {
        return TaskDto.builder()
                .Id(taskEntity.getId())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .build();
    }
}
