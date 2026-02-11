package com.david.question5_todo_list_api.service;

import com.david.question5_todo_list_api.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Optional<Task> getTaskById(Long id) {
        return tasks.stream().filter(task -> task.getTaskId().equals(id)).findFirst();
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return tasks.stream().filter(task -> task.isCompleted() == completed).collect(Collectors.toList());
    }

    public List<Task> getTasksByPriority(String priority) {
        return tasks.stream().filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    public Task createTask(Task task) {
        task.setTaskId(nextId++);
        tasks.add(task);
        return task;
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        Optional<Task> taskOptional = getTaskById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            task.setPriority(taskDetails.getPriority());
            task.setDueDate(taskDetails.getDueDate());
            return Optional.of(task);
        }
        return Optional.empty();
    }

    public boolean markTaskComplete(Long id) {
        Optional<Task> taskOptional = getTaskById(id);
        if (taskOptional.isPresent()) {
            taskOptional.get().setCompleted(true);
            return true;
        }
        return false;
    }

    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getTaskId().equals(id));
    }
}
