package org.lavertis.tasktrackerapi.dto.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateTaskRequest {
    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 64, message = "Title cannot be longer than 64 characters")
    private String title;

    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @NotNull(message = "Priority cannot be empty")
    private Integer priority;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private Date dueDate;

    private List<UUID> tags;
}
