package com.lavertis.tasktrackerapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateTaskRequest;
import com.lavertis.tasktrackerapi.entities.Task;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.services.task_service.ITaskService;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    final ITaskService taskService;
    final IUserService userService;

    public TaskController(ITaskService taskService, IUserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    @SecurityRequirements
    public ResponseEntity<List<Task>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("{id}/user")
    public ResponseEntity<Task> getUserTaskById(@PathVariable Long id) throws NotFoundException, ForbiddenRequestException {
        var userId = userService.getRequestUserId();
        if (taskService.isUserNotTaskOwner(id, userId))
            throw new ForbiddenRequestException("You are not allowed to get this task");

        var task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<Task>> getUserTasks() throws NotFoundException {
        var userId = userService.getRequestUserId();
        var tasks = taskService.getUserTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) throws NotFoundException {
        var task = taskService.createTask(request);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("{id}/user")
    public ResponseEntity<Task> updateUserTask(@PathVariable Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException, ForbiddenRequestException {
        var userId = userService.getRequestUserId();
        if (taskService.isUserNotTaskOwner(id, userId))
            throw new ForbiddenRequestException("You are not allowed to update this task");

        var task = taskService.updateTaskById(id, patch);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("{id}/user")
    public ResponseEntity<Void> deleteUserTaskById(@PathVariable Long id) throws NotFoundException, BadRequestException, ForbiddenRequestException {
        var userId = userService.getRequestUserId();
        if (taskService.isUserNotTaskOwner(id, userId))
            throw new ForbiddenRequestException("You are not allowed to delete this task");

        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
