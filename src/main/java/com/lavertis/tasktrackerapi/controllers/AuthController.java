package com.lavertis.tasktrackerapi.controllers;


import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.services.user_service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirements // remove padlock from endpoints
public class AuthController {

    final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public void login(@RequestBody CreateUserRequest request) {
        // Only for swagger endpoint
        // Logic is handled elsewhere
    }

    @PostMapping("register")
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequest request) throws BadRequestException {
        var user = userService.create(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
