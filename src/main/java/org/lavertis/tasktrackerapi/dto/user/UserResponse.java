package org.lavertis.tasktrackerapi.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
