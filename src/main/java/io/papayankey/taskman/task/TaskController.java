package io.papayankey.taskman.task;

import io.papayankey.taskman.util.CustomServerResponse;
import io.papayankey.taskman.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<CustomServerResponse> findAll() {
        List<TaskResponse> taskResponses = taskService.getTasks();
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<CustomServerResponse> findAllByStatus(@RequestParam @ValidTaskStatus String status) {
        List<TaskResponse> taskResponses = taskService.getTasksByStatus(status);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomServerResponse> findOne(@PathVariable Integer id) {
        TaskResponse taskResponse = taskService.getTask(id);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponse)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomServerResponse> create(@RequestBody @Valid TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.CREATED.value())
                .title(HttpStatus.CREATED.name())
                .detail("Task create successful")
                .data(taskResponse)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomServerResponse> delete(@PathVariable Integer id) {
        TaskResponse taskResponse = taskService.deleteTask(id);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .title(HttpStatus.ACCEPTED.name())
                .detail("Task delete successful")
                .data(taskResponse)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomServerResponse> update(@PathVariable Integer id, @RequestBody @Valid TaskRequest taskRequest) {
        taskService.updateTask(id, taskRequest);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .title(HttpStatus.NOT_EXTENDED.name())
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.NO_CONTENT);
    }
}