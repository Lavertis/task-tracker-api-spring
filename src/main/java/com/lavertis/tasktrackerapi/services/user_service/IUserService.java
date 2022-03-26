package com.lavertis.tasktrackerapi.services.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();

    User findUserById(long id) throws NotFoundException;

    User findUserByUsername(String username) throws NotFoundException;

    User createUser(CreateUserRequest request) throws BadRequestException;

    User updateUserById(long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException, ForbiddenRequestException;

    void deleteUserById(long id) throws NotFoundException, BadRequestException, ForbiddenRequestException;
}
