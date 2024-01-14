package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.User;

public interface IUserService {
    UserResponse getUserByUsername(String username);
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(String username, UpdateUserRequest updateUserRequest);
    boolean deleteUser(String username);
}
