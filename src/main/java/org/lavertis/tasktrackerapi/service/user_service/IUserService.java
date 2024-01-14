package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.request.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.request.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.entity.User;

public interface IUserService {
    User getUserByUsername(String username);
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(User user, UpdateUserRequest updateUserRequest);
}
