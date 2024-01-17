package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.converter.UserMapper;
import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private IUserService userService;
    private UserMapper userMapper;

    @PostMapping()
    @Operation(summary = "User sign up")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get current user")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        AppUser user = userService.getUserByUsername(principal.getName());
        UserResponse userResponse = userMapper.mapUserToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update current user")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @RequestBody @Valid UpdateUserRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), request));
    }

    @DeleteMapping("/current")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete current user")
    public ResponseEntity<Boolean> deleteCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.deleteUser(principal.getName()));
    }
}
