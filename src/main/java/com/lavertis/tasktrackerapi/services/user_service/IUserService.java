package com.lavertis.tasktrackerapi.services.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findById(long id);

    User create(CreateUserRequest request);

    User updateById(long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    void deleteById(long id);
}
