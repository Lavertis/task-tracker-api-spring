package com.lavertis.tasktrackerapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        var users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable long id) throws NotFoundException {
        var user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequest request) {
        var user = userService.create(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping(value = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<User> updateById(@PathVariable long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException {
        var user = userService.updateById(id, patch);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}