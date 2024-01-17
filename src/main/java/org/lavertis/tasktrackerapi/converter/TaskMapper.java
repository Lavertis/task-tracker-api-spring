package org.lavertis.tasktrackerapi.converter;

import org.lavertis.tasktrackerapi.dto.tag.TagResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.lavertis.tasktrackerapi.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse mapTaskToTaskResponse(Task task);

    @Mapping(target = "tags", ignore = true)
    Task mapCreateTaskRequestToTask(CreateTaskRequest request);
}
