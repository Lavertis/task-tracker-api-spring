package com.lavertis.tasktrackerapi.services.task_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateTaskRequest;
import com.lavertis.tasktrackerapi.entities.Task;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.repositories.TaskRepository;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    final TaskRepository taskRepository;
    final IUserService userService;

    public TaskService(TaskRepository taskRepository, IUserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public boolean isUserNotTaskOwner(Long userId, Long taskId) throws NotFoundException {
        var user = userService.findUserById(userId);
        return !taskRepository.existsTaskByIdAndTaskOwnersContaining(taskId, user);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) throws NotFoundException {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
    }

    @Override
    public Task createTask(CreateTaskRequest request) throws NotFoundException {
        List<User> usersList = new ArrayList<>();
        for (var userId : request.getAssignedUsersIds()) {
            var user = userService.findUserById(userId);
            usersList.add(user);
        }
        var task = new Task(request.getName(), request.getDescription(), request.getFinishDate(), usersList);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) throws NotFoundException, BadRequestException {
        var userId = userService.getAuthId();
        User user = userService.findUserById(userId);
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        if (!task.getTaskOwners().contains(user))
            throw new BadRequestException("User who made the request is not the task owner");
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getUserTasks(Long userId) throws NotFoundException {
        User user = userService.findUserById(userId);
        return taskRepository.findTasksByTaskOwnersContaining(user);
    }

    @Override
    public Task updateTaskById(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException {
        var task = getTaskById(id);
        Task taskPatched = applyPatchToTask(patch, task);
        taskRepository.save(taskPatched);
        return taskPatched;
    }

    private Task applyPatchToTask(JsonPatch patch, Task targetTask) throws JsonPatchException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetTask, JsonNode.class));
        return objectMapper.treeToValue(patched, Task.class);
    }
}
