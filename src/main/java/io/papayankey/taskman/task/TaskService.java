package io.papayankey.taskman.task;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<Task> getTask(Integer id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByCompleted(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Integer id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
           Task task = optionalTask.get();
           task.setDescription(taskDto.getDescription());
           task.setCompleted(taskDto.isCompleted());
           task.setUpdatedAt(LocalDateTime.now());

           taskRepository.save(task);
        }
    }
}
