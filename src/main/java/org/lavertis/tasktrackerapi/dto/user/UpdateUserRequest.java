package org.lavertis.tasktrackerapi.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
