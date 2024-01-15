package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;

public interface IUserService {
    UserResponse getUserByUsername(String username);
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(String username, UpdateUserRequest updateUserRequest);
    boolean deleteUser(String username);
}
