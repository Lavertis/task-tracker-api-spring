package com.lavertis.tasktrackerapi.dto;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class CreateTaskRequest {
    List<Long> assignedUsersIds;
    private String name;
    private String description;
    private Date finishDate;
}
