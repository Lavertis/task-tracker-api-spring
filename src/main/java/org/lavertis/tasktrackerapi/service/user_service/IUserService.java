package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.request.CreateUserRequest;
import org.lavertis.tasktrackerapi.entity.User;

public interface IUserService {
    User createUser(CreateUserRequest createUserRequest);
}
