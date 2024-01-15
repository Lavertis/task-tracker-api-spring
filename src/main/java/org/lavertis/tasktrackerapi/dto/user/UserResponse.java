package org.lavertis.tasktrackerapi.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
