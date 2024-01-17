package org.lavertis.tasktrackerapi.service.user_service;

import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.converter.UserMapper;
import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {
    private IUserRepository userRepository;
    private PasswordEncoder bcryptEncoder;
    private UserMapper userMapper;

    @Override
    public AppUser getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        AppUser user = userMapper.mapCreateUserRequestToUser(createUserRequest);
        user.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
        user = userRepository.save(user);
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        AppUser user = userRepository.findById(userId).orElseThrow();
        if (updateUserRequest.getEmail() != null)
            user.setUsername(updateUserRequest.getEmail());
        if (updateUserRequest.getFirstName() != null)
            user.setFirstName(updateUserRequest.getFirstName());
        if (updateUserRequest.getLastName() != null)
            user.setLastName(updateUserRequest.getLastName());
        if (updateUserRequest.getPassword() != null)
            user.setPassword(bcryptEncoder.encode(updateUserRequest.getPassword()));
        user = userRepository.save(user);
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public boolean deleteUser(UUID userId) {
        AppUser user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.findByUsername(email) != null;
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
