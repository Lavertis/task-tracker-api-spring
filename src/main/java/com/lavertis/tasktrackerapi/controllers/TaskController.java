package com.lavertis.tasktrackerapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateTaskRequest;
import com.lavertis.tasktrackerapi.entities.Task;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.services.task_service.ITaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @SecurityRequirements
    public ResponseEntity<List<Task>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable long id) throws NotFoundException {
        var tasks = taskService.getUserTasks(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) throws NotFoundException {
        var task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) throws NotFoundException {
        var task = taskService.createTask(request);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException {
        var task = taskService.updateTaskById(id, patch);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable long id) throws NotFoundException, BadRequestException {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
