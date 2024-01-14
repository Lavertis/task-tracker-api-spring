package org.lavertis.tasktrackerapi.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;
    private Date dueDate;
    private UUID userId;
}
