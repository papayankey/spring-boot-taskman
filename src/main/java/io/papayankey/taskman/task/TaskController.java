package io.papayankey.taskman.task;

import io.papayankey.taskman.exceptions.CustomResponse;
import io.papayankey.taskman.exceptions.ResponseHandler;
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
    public ResponseEntity<CustomResponse> getTasks() {
       List<Task> tasks = taskService.getTasks();
       return ResponseHandler.builder()
               .message("Ok")
               .status(HttpStatus.OK)
               .data(tasks)
               .build();
    }

    @GetMapping(path = "/tasks-status")
    public ResponseEntity<CustomResponse> getTasksByCompleted(@RequestParam String completed) {
        List<Task> tasksByCompleted = taskService.getTasksByCompleted(Boolean.parseBoolean(completed));
        return ResponseHandler.builder()
                .message("Ok")
                .status(HttpStatus.OK)
                .data(tasksByCompleted)
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> getTask(@PathVariable String id) {
        Task task = taskService.getTask(Integer.parseInt(id));
        return ResponseHandler.builder()
                .message("Ok")
                .status(HttpStatus.OK)
                .data(task)
                .build();
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.createTask(taskDto);
        return ResponseHandler.builder()
                .message("Task created")
                .status(HttpStatus.CREATED)
                .data(task)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> deleteTask(@PathVariable String id) {
        Task task = taskService.deleteTask(Integer.parseInt(id));
        return ResponseHandler.builder()
                .message("Task deleted")
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> updateTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        Task task = taskService.updateTask(id, taskDto);
        return ResponseHandler.builder()
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
