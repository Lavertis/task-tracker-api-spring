package org.lavertis.tasktrackerapi.dto.task;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;
    private Date dueDate;
}
