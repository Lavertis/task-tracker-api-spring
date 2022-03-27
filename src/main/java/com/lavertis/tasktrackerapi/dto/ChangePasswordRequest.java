package com.lavertis.tasktrackerapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordRequest {
    @Size(min = 6, max = 16, message = "length must be between 6 and 16")
    private String password;
}
