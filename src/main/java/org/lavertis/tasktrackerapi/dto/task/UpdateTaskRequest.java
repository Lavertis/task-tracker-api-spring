package org.lavertis.tasktrackerapi.dto.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateTaskRequest {
    @Size(max = 64, message = "Title cannot be longer than 64 characters")
    private String title;

    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    private Boolean completed;

    private Integer priority;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private Date dueDate;

    private List<UUID> tags;
}
