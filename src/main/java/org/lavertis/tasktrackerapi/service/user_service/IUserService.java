package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.AppUser;

import java.util.UUID;

public interface IUserService {
    AppUser getUserById(UUID id);
    AppUser getUserByUsername(String username);
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(String username, UpdateUserRequest updateUserRequest);
    boolean deleteUser(String username);
    boolean isEmailExist(String email);
}
