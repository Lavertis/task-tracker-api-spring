package org.lavertis.tasktrackerapi.service.task_service;

import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskService {
    TaskResponse getTaskById(UUID id);
    PagedResponse<TaskResponse> getTasks(String username);
    TaskResponse createTask(CreateTaskRequest request, String username);
    TaskResponse updateTask(UUID id, UpdateTaskRequest request);
    void deleteTask(UUID id);
}
