package io.papayankey.taskman.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
