package org.lavertis.tasktrackerapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse <T>{
    private List<T> items;
    private Long totalCount;
}
