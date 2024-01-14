package org.lavertis.tasktrackerapi.controller;

import org.lavertis.tasktrackerapi.dto.request.CreateUserRequest;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<?> saveUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }
}
