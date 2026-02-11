package com.david.question5_todo_list_api.controller;

import com.david.question5_todo_list_api.model.Task;
import com.david.question5_todo_list_api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Task task1 = new Task(1L, "Task 1", "Desc 1", false, "LOW", "2023-12-31");
        Task task2 = new Task(2L, "Task 2", "Desc 2", true, "HIGH", "2023-12-31");
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Task 1", "Desc 1", false, "LOW", "2023-12-31");
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task(null, "New Task", "New Desc", false, "MEDIUM", "2024-01-01");
        Task savedTask = new Task(1L, "New Task", "New Desc", false, "MEDIUM", "2024-01-01");
        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(1));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task taskDetails = new Task(null, "Updated Task", "Updated Desc", true, "HIGH", "2024-01-01");
        Task updatedTask = new Task(1L, "Updated Task", "Updated Desc", true, "HIGH", "2024-01-01");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(Optional.of(updatedTask));

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
