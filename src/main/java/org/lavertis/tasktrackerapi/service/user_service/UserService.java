package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UpdateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.User;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public User createUser(CreateUserRequest createUserRequest) {
        User newUser = new User();
        newUser.setUsername(createUserRequest.getEmail());
        newUser.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(String username, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByUsername(username);
        if (updateUserRequest.getEmail() != null)
            user.setUsername(updateUserRequest.getEmail());
        if (updateUserRequest.getFirstName() != null)
            user.setFirstName(updateUserRequest.getFirstName());
        if (updateUserRequest.getLastName() != null)
            user.setLastName(updateUserRequest.getLastName());
        if (updateUserRequest.getPassword() != null)
            user.setPassword(bcryptEncoder.encode(updateUserRequest.getPassword()));
        return userRepository.save(user);
    }
}
