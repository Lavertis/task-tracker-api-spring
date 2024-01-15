package org.lavertis.tasktrackerapi.dto.task;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;
    private Date dueDate;
    private UUID userId;
}
