package org.lavertis.tasktrackerapi.dto.task;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateTaskRequest {
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;
    private Date dueDate;
}
