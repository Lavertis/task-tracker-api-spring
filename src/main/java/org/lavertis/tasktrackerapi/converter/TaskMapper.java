package org.lavertis.tasktrackerapi.converter;

import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse mapTaskToTaskResponse(Task task);

    Task mapCreateTaskRequestToTask(CreateTaskRequest createTaskRequest);
}
