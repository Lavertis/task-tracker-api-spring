package org.lavertis.tasktrackerapi.dto.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
