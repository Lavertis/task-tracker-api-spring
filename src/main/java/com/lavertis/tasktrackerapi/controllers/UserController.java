package com.lavertis.tasktrackerapi.controllers;

import com.lavertis.tasktrackerapi.dto.ChangePasswordRequest;
import com.lavertis.tasktrackerapi.dto.ChangeUsernameRequest;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "Returns all users. Only available for admin.")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Returns a user by id. Only available for admin.")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws NotFoundException {
        var user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("auth")
    @Operation(summary = "Returns authenticated user.")
    public ResponseEntity<User> getAuthenticatedUser() throws NotFoundException {
        var id = userService.getAuthId();
        var user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    @SecurityRequirements
    @Operation(summary = "Creates a new user.")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) throws BadRequestException {
        var user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/auth/username")
    @Operation(summary = "Changes username of authenticated user.")
    public ResponseEntity<User> changeAuthenticatedUserUsername(@Valid @RequestBody ChangeUsernameRequest request) throws NotFoundException, BadRequestException {
        var id = userService.getAuthId();
        var user = userService.changeUserUsername(id, request.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/auth/password")
    @Operation(summary = "Changes password of authenticated user.")
    public ResponseEntity<User> changeAuthenticatedUserPassword(@Valid @RequestBody ChangePasswordRequest request) throws NotFoundException {
        var id = userService.getAuthId();
        var user = userService.changeUserPassword(id, request.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("auth")
    @Operation(summary = "Deletes authenticated user.")
    public ResponseEntity<Void> deleteAuthenticatedUser() throws NotFoundException, BadRequestException, ForbiddenRequestException {
        var id = userService.getAuthId();
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}