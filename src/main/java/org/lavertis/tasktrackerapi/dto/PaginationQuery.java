package org.lavertis.tasktrackerapi.dto;

import lombok.Data;

@Data
public class PaginationQuery {
    private Integer rangeStart;
    private Integer rangeEnd;
}
