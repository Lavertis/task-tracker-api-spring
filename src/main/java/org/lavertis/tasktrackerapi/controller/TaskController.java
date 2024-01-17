package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.config.AppUserAuthentication;
import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskQuery;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import org.lavertis.tasktrackerapi.service.task_service.ITaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/tasks")
public class TaskController {
    private ITaskService taskService;

    @GetMapping("{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable UUID id,
            AppUserAuthentication auth
    ) throws ForbiddenRequestException {
        if (!taskService.isUserTaskOwner(id, auth.getPrincipal().getId()))
            throw new ForbiddenRequestException("User is not an owner of the task");
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Get all User's tasks")
    public ResponseEntity<PagedResponse<TaskResponse>> getTasks(
            @ModelAttribute TaskQuery taskQuery,
            AppUserAuthentication auth
    ) {
        return ResponseEntity.ok(taskService.getUserTasks(taskQuery, auth.getPrincipal().getId()));
    }

    @PostMapping
    @Operation(summary = "Create task")
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid CreateTaskRequest request,
            AppUserAuthentication auth
    ) {
        return ResponseEntity.ok(taskService.createTask(request, auth.getPrincipal().getId()));
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTaskRequest request,
            AppUserAuthentication auth
    ) throws ForbiddenRequestException {
        if (!taskService.isUserTaskOwner(id, auth.getPrincipal().getId()))
            throw new ForbiddenRequestException("User is not an owner of the task");
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<Boolean> deleteTask(
            @PathVariable UUID id,
            AppUserAuthentication auth
    ) throws ForbiddenRequestException {
        if (!taskService.isUserTaskOwner(id, auth.getPrincipal().getId()))
            throw new ForbiddenRequestException("User is not an owner of the task");
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
