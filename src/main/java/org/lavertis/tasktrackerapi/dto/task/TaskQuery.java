package org.lavertis.tasktrackerapi.dto.task;

import lombok.Getter;
import lombok.Setter;
import org.lavertis.tasktrackerapi.dto.PaginationQuery;

@Getter
@Setter
public class TaskQuery extends PaginationQuery {
    private String searchTitle;
    private Boolean hideCompleted;
    private Integer priority = null;
}
