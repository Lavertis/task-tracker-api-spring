package org.lavertis.tasktrackerapi.controller;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
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

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }

    @PatchMapping("/current")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UpdateUserRequest updateUserRequest, Principal principal) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), updateUserRequest));
    }

    @DeleteMapping("/current")
    public ResponseEntity<?> deleteCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.deleteUser(principal.getName()));
    }
}
