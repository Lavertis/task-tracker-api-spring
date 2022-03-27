package com.lavertis.tasktrackerapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    final IUserService userService;

    public AccountController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getAccountDetails() throws NotFoundException {
        var id = userService.getRequestUserId();
        var user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    @SecurityRequirements
    public ResponseEntity<User> createAccount(@Valid @RequestBody CreateUserRequest request) throws BadRequestException {
        var user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping(consumes = "application/json-patch+json")
    public ResponseEntity<User> updateAccount(@RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException, ForbiddenRequestException {
        var id = userService.getRequestUserId();
        var user = userService.updateUserById(id, patch);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAccount() throws NotFoundException, BadRequestException, ForbiddenRequestException {
        var id = userService.getRequestUserId();
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}