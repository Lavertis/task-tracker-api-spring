package org.lavertis.tasktrackerapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse<T> {
    private List<T> items;
    private Long totalCount;
}
