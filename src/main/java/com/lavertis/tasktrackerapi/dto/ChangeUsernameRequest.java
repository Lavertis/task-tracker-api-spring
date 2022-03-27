package com.lavertis.tasktrackerapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangeUsernameRequest {
    @Size(min = 4, max = 12, message = "length must be between 4 and 12")
    private String username;
}
