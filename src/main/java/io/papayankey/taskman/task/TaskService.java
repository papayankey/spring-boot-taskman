package io.papayankey.taskman.task;

import io.papayankey.taskman.exception.TaskNotFoundException;
import io.papayankey.taskman.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskResponse> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskMapper.toTaskReponseList(taskEntities);
    }

    public TaskResponse createTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = taskMapper.toTaskEntity(taskRequest);
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskEntity.setUserEntity(currentUser);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return taskMapper.toTaskResponse(savedTaskEntity);
    }

    public TaskResponse getTask(int id) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        TaskEntity taskEntity = optionalTask.orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toTaskResponse(taskEntity);
    }

    public List<TaskResponse> getTasksByStatus(String taskStatus) {
        List<TaskEntity> taskEntities = taskRepository.findByStatus(TaskStatus.valueOf(taskStatus.toUpperCase()));
        return taskMapper.toTaskReponseList(taskEntities);
    }

    public TaskResponse deleteTask(int id) {
        TaskResponse taskResponse = getTask(id);
        taskRepository.deleteById(id);
        return taskResponse;
    }

    public void updateTask(int id, TaskRequest taskRequest) {
        TaskStatus taskStatus = TaskStatus.valueOf(taskRequest.getStatus().toUpperCase());
        taskRepository.findByIdAndUpdate(id, taskRequest.getDescription(), taskStatus);
    }
}
