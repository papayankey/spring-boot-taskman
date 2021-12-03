package io.papayankey.taskman.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping(path = "/tasks-status")
    public List<Task> getTasksByCompleted(@RequestParam String completed) {
        return taskService.getTasksByCompleted(Boolean.parseBoolean(completed));
    }

    @GetMapping(path = "/{id}")
    public Optional<Task> getTask(@PathVariable String id) {
        return taskService.getTask(Integer.parseInt(id));
    }

    @PostMapping
    public Task createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(Integer.parseInt(id));
    }

    @PutMapping(path = "/{id}")
    public void updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
    }

}
