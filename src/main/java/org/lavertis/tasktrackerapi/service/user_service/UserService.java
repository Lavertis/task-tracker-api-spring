package org.lavertis.tasktrackerapi.service.user_service;

import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.converter.UserMapper;
import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.User;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private PasswordEncoder bcryptEncoder;
    private UserMapper userMapper;

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.mapCreateUserRequestToUser(createUserRequest);
        user.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
        user = userRepository.save(user);
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByUsername(username);
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
    public boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username);
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
}
