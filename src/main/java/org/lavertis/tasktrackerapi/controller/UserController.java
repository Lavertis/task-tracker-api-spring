package org.lavertis.tasktrackerapi.controller;

import org.lavertis.tasktrackerapi.dto.request.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.request.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.entity.User;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @PatchMapping("/current")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UpdateUserRequest updateUserRequest, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(userService.updateUser(user, updateUserRequest));
    }
}
