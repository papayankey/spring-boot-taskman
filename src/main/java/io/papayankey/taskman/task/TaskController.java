package io.papayankey.taskman.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping(path = "/active")
    public List<Task> getActiveTasks() {
       return taskService.getActiveTasks();
    }

    @GetMapping(path = "/completed")
    public List<Task> getCompletedTasks() {
        return taskService.getCompletedTasks();
    }

    @GetMapping(path = "/{id}")
    public Optional<Task> getTask(@PathVariable(value = "id") String id) {
        return taskService.getTask(Integer.parseInt(id));
    }

    @PostMapping(path = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

}
