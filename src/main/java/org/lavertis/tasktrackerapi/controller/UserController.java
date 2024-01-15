package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private IUserService userService;

    @PostMapping()
    @Operation(summary = "User sign up")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @GetMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get current user")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }

    @PatchMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update current user")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody UpdateUserRequest updateUserRequest, Principal principal) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), updateUserRequest));
    }

    @DeleteMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete current user")
    public ResponseEntity<Boolean> deleteCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.deleteUser(principal.getName()));
    }
}
