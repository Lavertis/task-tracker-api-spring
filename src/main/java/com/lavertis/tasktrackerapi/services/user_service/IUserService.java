package com.lavertis.tasktrackerapi.services.user_service;

import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;

import java.util.List;

public interface IUserService {
    Long getAuthId();

    List<User> findAllUsers();

    User findUserById(Long id) throws NotFoundException;

    User createUser(CreateUserRequest request) throws BadRequestException;

    User changeUserPassword(Long id, String newPassword) throws NotFoundException;

    User changeUserUsername(Long id, String newUsername) throws NotFoundException, BadRequestException;

    void deleteUserById(Long id) throws NotFoundException, BadRequestException, ForbiddenRequestException;
}
