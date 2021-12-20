package io.papayankey.taskman.task;

import io.papayankey.taskman.util.CustomResponse;
import io.papayankey.taskman.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<CustomResponse> getTasks(@RequestParam(name = "completed", required = false) Boolean completed) {
        List<Task> tasks;
        if (completed != null) tasks = taskService.getTasksByCompleted(completed);
        else tasks = taskService.getTasks();

        CustomResponse customResponse = CustomResponse.builder()
                .message("OK")
                .status(HttpStatus.OK)
                .data(convertToListDto(tasks))
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> getTask(@PathVariable Integer id) {
        Task task = taskService.getTask(id);
        CustomResponse customResponse = CustomResponse.builder()
                .message("OK")
                .status(HttpStatus.OK)
                .data(convertToDto(task))
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createTask(@RequestBody TaskDto taskDto) {
       Task task = taskService.createTask(convertToEntity(taskDto));
        CustomResponse customResponse = CustomResponse.builder()
                .message("Task create successful")
                .status(HttpStatus.OK)
                .data(convertToDto(task))
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> deleteTask(@PathVariable Integer id) {
        Task task = taskService.deleteTask(id);
        CustomResponse customResponse = CustomResponse.builder()
                .message("Task delete successful")
                .status(HttpStatus.ACCEPTED)
                .data(convertToDto(task))
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, convertToEntity(taskDto));
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.NO_CONTENT);
    }

    private Task convertToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        return task;
    }

    private TaskDto convertToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setCompleted(task.isCompleted());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());
        return taskDto;
    }

    private List<TaskDto> convertToListDto(List<Task> tasks) {
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
