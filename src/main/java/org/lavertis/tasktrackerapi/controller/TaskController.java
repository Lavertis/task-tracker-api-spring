package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskQuery;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.service.task_service.ITaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<?> getTaskById(@PathVariable UUID id, Principal principal) {
        // TODO: check if user is owner of task
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Get all User's tasks")
    public ResponseEntity<?> getTasks(@ModelAttribute TaskQuery taskQuery, Principal principal) {
        return ResponseEntity.ok(taskService.getTasks(taskQuery, principal.getName()));
    }

    @PostMapping
    @Operation(summary = "Create task")
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid CreateTaskRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(taskService.createTask(request, principal.getName()));
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTaskRequest request,
            Principal principal
    ) {
        // TODO: check if user is owner of task
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<Boolean> deleteTask(@PathVariable UUID id, Principal principal) {
        // TODO: check if user is owner of task
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
