package org.lavertis.tasktrackerapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.lavertis.tasktrackerapi.validation.unique_username.UniqueUsername;


@Getter
@Setter
public class CreateUserRequest {
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @UniqueUsername(message = "Email already exists")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Min(value = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
}