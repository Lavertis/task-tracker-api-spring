package com.lavertis.tasktrackerapi.services.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findById(long id) throws NotFoundException;

    User findByUsername(String username) throws NotFoundException;

    User create(CreateUserRequest request) throws BadRequestException;

    User updateById(long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException;

    void deleteById(long id);
}
