package io.papayankey.taskman.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.papayankey.taskman.security.CustomUserDetailsService;
import io.papayankey.taskman.dto.TaskDto;
import io.papayankey.taskman.enums.TaskStatus;
import io.papayankey.taskman.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TaskController.class})
@WithMockUser(username = "ben")
class TaskControllerTest {
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnListOfTasks() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", empty()));
    }

    @Test
    public void shouldReturnCreatedTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .Id(2)
                .description("go the gym")
                .status(TaskStatus.ACTIVE)
                .build();

        when(taskService.createTask(any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(post("/api/tasks")
                        .content(objectMapper.writeValueAsString(taskDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Task create successful")))
                .andExpect(jsonPath("$.data.id", is(2)));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .description("practice more testing")
                .status(TaskStatus.ACTIVE)
                .build();

        mockMvc.perform(put("/api/tasks/{id}", 1)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).updateTask(1, taskDto);
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .Id(5)
                .description("read on spring cloud")
                .status(TaskStatus.COMPLETED)
                .build();

        when(taskService.deleteTask(anyInt())).thenReturn(taskDto);

        mockMvc.perform(delete("/api/tasks/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message", is("Task delete successful")))
                .andExpect(jsonPath("$.data.description", is("read on spring cloud")));
    }
}