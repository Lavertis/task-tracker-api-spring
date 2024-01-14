package org.lavertis.tasktrackerapi.service.task_service;

import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.entity.Task;
import org.lavertis.tasktrackerapi.entity.User;
import org.lavertis.tasktrackerapi.repository.ITaskRepository;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public PagedResponse<TaskResponse> getTasks(String username) {
        List<Task> tasks = taskRepository.findAllByUserUsername(username);
        List<TaskResponse> taskResponses = tasks.stream().map(task -> TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build()
        ).toList();
        PagedResponse<TaskResponse> response = new PagedResponse<>();
        response.setTotalCount(tasks.size());
        response.setItems(taskResponses);
        return response;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .user(user)
                .build();
        task = taskRepository.save(task);
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (request.getTitle() != null)
            task.setTitle(request.getTitle());
        if (request.getDescription() != null)
            task.setDescription(request.getDescription());
        if (request.getCompleted() != null)
            task.setCompleted(request.getCompleted());
        if (request.getPriority() != null)
            task.setPriority(request.getPriority());
        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());
        task = taskRepository.save(task);
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
