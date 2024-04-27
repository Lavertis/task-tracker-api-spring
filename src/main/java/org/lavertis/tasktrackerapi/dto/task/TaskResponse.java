package org.lavertis.tasktrackerapi.dto.task;

import lombok.Getter;
import lombok.Setter;
import org.lavertis.tasktrackerapi.dto.tag.TagResponse;

import java.util.Date;
import java.util.List;
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
    private List<TagResponse> tags;
}
