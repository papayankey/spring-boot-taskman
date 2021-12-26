package io.papayankey.taskman.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    private static String convertToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void shouldReturnEmptyTaskList() throws Exception {
        mockMvc.perform(get("/api/v1/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void shouldReturnCreatedTask() throws Exception {
        Task newTask = new Task(null, "go to the gym", false, null, null);

        LocalDateTime localDateTime = LocalDateTime.now();
        Task createdTask = new Task(1, "go to the gym", false, localDateTime, localDateTime);

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);

        mockMvc.perform(post("/api/v1/tasks")
                        .content(convertToJsonString(newTask))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Task create successful"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Task newUpdate = new Task(null, "spring boot is awesome", false, null, null);
        Task updatedTask = new Task(1, "spring boot is super awesome", false, localDateTime, LocalDateTime.now());

        when(taskService.updateTask(anyInt(), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/v1/tasks/1")
                        .content(convertToJsonString(newUpdate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Task deletedTask = new Task(5, "practice more spring boot", true, localDateTime, localDateTime);

        when(taskService.deleteTask(5)).thenReturn(deletedTask);

        mockMvc.perform(delete("/api/v1/tasks/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("Task delete successful"))
                .andExpect(jsonPath("$.data.completed").value(true));
    }
}