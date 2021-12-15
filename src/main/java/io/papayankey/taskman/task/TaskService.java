package io.papayankey.taskman.task;

import io.papayankey.taskman.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        return taskRepository.save(task);
    }

    public Task getTask(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getTasksByCompleted(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public Task deleteTask(int id) {
        Task task = getTask(id);
        taskRepository.deleteById(id);
        return task;
    }

    public Task updateTask(int id, TaskDto taskDto) {
        Task task = getTask(id);
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
}
