package org.lavertis.tasktrackerapi.validation.unique_username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final IUserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty())
            return true;
        return !userService.isEmailExist(email);
    }
}