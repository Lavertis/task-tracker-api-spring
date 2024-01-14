package org.lavertis.tasktrackerapi.dto.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SignInRequest {
    private String email;
    private String password;
}