package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.request.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.request.user.UpdateUserRequest;
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
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public User updateUser(User user, UpdateUserRequest updateUserRequest) {
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
