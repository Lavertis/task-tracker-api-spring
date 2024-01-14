package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.service.task_service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id, Principal principal) {
        // TODO: check if user is owner of task
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Get all User's tasks")
    public ResponseEntity<?> getTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getTasks(principal.getName()));
    }

    @PostMapping
    @Operation(summary = "Create task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request, Principal principal) {
        return ResponseEntity.ok(taskService.createTask(request, principal.getName()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody UpdateTaskRequest request, Principal principal) {
        // TODO: check if user is owner of task
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id, Principal principal) {
        // TODO: check if user is owner of task
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
