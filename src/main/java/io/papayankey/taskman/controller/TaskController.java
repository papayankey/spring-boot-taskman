package io.papayankey.taskman.controller;

import io.papayankey.taskman.dto.TaskDto;
import io.papayankey.taskman.service.TaskService;
import io.papayankey.taskman.util.CustomResponse;
import io.papayankey.taskman.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<CustomResponse> getTasks(@RequestParam(name = "status", required = false) String status) {
        List<TaskDto> taskDtos;
        if (status != null) taskDtos = taskService.getTasksByStatus(status);
        else taskDtos = taskService.getTasks();

        CustomResponse customResponse = CustomResponse.builder()
                .message("OK")
                .status(HttpStatus.OK.value())
                .data(taskDtos)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> getTask(@PathVariable Integer id) {
        TaskDto taskDto = taskService.getTask(id);
        CustomResponse customResponse = CustomResponse.builder()
                .message("OK")
                .status(HttpStatus.OK.value())
                .data(taskDto)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createTask(@RequestBody TaskDto taskDto) {
        TaskDto savedTask = taskService.createTask(taskDto);
        CustomResponse customResponse = CustomResponse.builder()
                .message("Task create successful")
                .status(HttpStatus.CREATED.value())
                .data(savedTask)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> deleteTask(@PathVariable Integer id) {
        TaskDto taskDto = taskService.deleteTask(id);
        CustomResponse customResponse = CustomResponse.builder()
                .message("Task delete successful")
                .status(HttpStatus.ACCEPTED.value())
                .data(taskDto)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.NO_CONTENT);
    }
}
