package io.papayankey.taskman.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.papayankey.taskman.security.CustomUserDetailsService;
import io.papayankey.taskman.security.SecurityConfiguration;
import io.papayankey.taskman.security.jwt.JWTService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {SecurityConfiguration.class})
@WebMvcTest(controllers = {TaskController.class})
@WithMockUser
class TaskControllerTest {
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskServiceImpl taskServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCreatedTask() throws Exception {
        TaskRequest taskRequest = TaskRequest.builder().description("go the gym").status(TaskStatus.ACTIVE.name()).build();
        TaskResponse taskResponse = TaskResponse.builder().id(1).description("go the gym").status(TaskStatus.ACTIVE.name()).build();

        when(taskServiceImpl.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(post("/api/v1/tasks")
                        .content(objectMapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.detail", is("Task create successful")))
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskRequest taskRequest = TaskRequest.builder().description("practice more testing").status(TaskStatus.ACTIVE.name()).build();

        mockMvc.perform(put("/api/v1/tasks/{id}", 1)
                        .content(objectMapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskServiceImpl, times(1)).updateTask(1, taskRequest);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        TaskResponse taskResponse = TaskResponse.builder().id(5).description("read on spring cloud").status(TaskStatus.COMPLETED.name()).build();

        when(taskServiceImpl.deleteTask(anyInt())).thenReturn(taskResponse);

        mockMvc.perform(delete("/api/v1/tasks/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.detail", is("Task delete successful")))
                .andExpect(jsonPath("$.data.description", is("read on spring cloud")));
    }

    @Nested
    @DisplayName("should get tasks as")
    class getTasks {
        @Test
        @DisplayName("empty list given no task added")
        void shouldReturnEmptyList() throws Exception {
            mockMvc.perform(get("/api/v1/tasks")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", empty()));
        }

        @Test
        @DisplayName("list of two tasks given two task added")
        void shouldReturnListOfTwoTasks() throws Exception {
            List<TaskResponse> taskResponseList = List.of(
                    TaskResponse.builder().id(1).description("Check on at least one co-worker each day").status(TaskStatus.ACTIVE.name()).build(),
                    TaskResponse.builder().id(2).description("Read on microsoft azure").status(TaskStatus.INACTIVE.name()).build()
            );

            when(taskServiceImpl.getTasks()).thenReturn(taskResponseList);

            mockMvc.perform(get("/api/v1/tasks")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.size()", is(2)));
        }
    }
}
