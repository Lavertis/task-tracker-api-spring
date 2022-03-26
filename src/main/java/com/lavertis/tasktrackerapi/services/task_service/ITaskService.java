package com.lavertis.tasktrackerapi.services.task_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateTaskRequest;
import com.lavertis.tasktrackerapi.entities.Task;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;

import java.util.List;

public interface ITaskService {
    List<Task> getAllTasks();

    Task getTaskById(long id) throws NotFoundException;

    Task createTask(CreateTaskRequest request) throws NotFoundException;

    void deleteTaskById(long id) throws NotFoundException, BadRequestException;

    List<Task> getUserTasks(long userId) throws NotFoundException;

    Task updateTaskById(long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException;
}