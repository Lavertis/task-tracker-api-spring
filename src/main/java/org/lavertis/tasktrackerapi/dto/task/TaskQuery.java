package org.lavertis.tasktrackerapi.dto.task;

import lombok.Data;
import org.lavertis.tasktrackerapi.dto.PaginationQuery;

@Data
public class TaskQuery extends PaginationQuery {
    private String searchTitle;
    private Boolean hideCompleted;
}
