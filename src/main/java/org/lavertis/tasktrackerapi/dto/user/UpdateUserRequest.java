package org.lavertis.tasktrackerapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.lavertis.tasktrackerapi.validation.unique_username.UniqueUsername;

@Getter
@Setter
public class UpdateUserRequest {
    @Email(message = "Email must be valid")
    @UniqueUsername(message = "Email already exists")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String firstName;

    private String lastName;
}
