package io.papayankey.taskman.task;

import io.papayankey.taskman.util.CustomServerResponse;
import io.papayankey.taskman.util.ResponseHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private TaskServiceImpl taskServiceImpl;

    @GetMapping
    public ResponseEntity<CustomServerResponse> findAll() {
        List<TaskResponse> taskResponses = taskServiceImpl.getTasks();
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<CustomServerResponse> findAllByStatus(@RequestParam @ValidTaskStatus String status) {
        List<TaskResponse> taskResponses = taskServiceImpl.getTasksByStatus(status);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomServerResponse> findOne(@PathVariable Integer id) {
        TaskResponse taskResponse = taskServiceImpl.getTask(id);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .title(HttpStatus.OK.name())
                .status(HttpStatus.OK.value())
                .data(taskResponse)
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomServerResponse> create(@RequestBody @Valid TaskRequest taskRequest) {
        TaskResponse taskResponse = taskServiceImpl.createTask(taskRequest);
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
        TaskResponse taskResponse = taskServiceImpl.deleteTask(id);
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
        taskServiceImpl.updateTask(id, taskRequest);
        CustomServerResponse customServerResponse = CustomServerResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .title(HttpStatus.NOT_EXTENDED.name())
                .build();
        return ResponseHandler.create(customServerResponse, HttpStatus.NO_CONTENT);
    }
}
