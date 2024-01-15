package org.lavertis.tasktrackerapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationQuery {
    private Integer rangeStart;
    private Integer rangeEnd;
}
