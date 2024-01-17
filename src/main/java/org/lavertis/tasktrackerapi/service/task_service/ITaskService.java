package org.lavertis.tasktrackerapi.service.task_service;

import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskQuery;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskService {
    TaskResponse getTaskById(UUID id);
    PagedResponse<TaskResponse> getUserTasks(TaskQuery taskQuery, UUID userId);
    TaskResponse createTask(CreateTaskRequest request, UUID userId);
    TaskResponse updateTask(UUID id, UpdateTaskRequest request);
    void deleteTask(UUID id);
    Boolean isUserTaskOwner(UUID taskId, UUID userId);
}
