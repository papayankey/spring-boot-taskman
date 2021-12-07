package io.papayankey.taskman.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        Optional<Task> task = taskService.getTask(Integer.parseInt(id));
        if (task.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.createTask(taskDto);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable String id) {
        Optional<Task> task = taskService.deleteTask(Integer.parseInt(id));
        if (task.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get(), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        Optional<Task> task = taskService.updateTask(id, taskDto);
        if (task.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
