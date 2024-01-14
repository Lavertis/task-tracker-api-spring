package org.lavertis.tasktrackerapi.service.user_service;

import org.lavertis.tasktrackerapi.dto.request.CreateUserRequest;
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

    public User createUser(CreateUserRequest createUserRequest) {
        User newUser = new User();
        newUser.setUsername(createUserRequest.getEmail());
        newUser.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        return userRepository.save(newUser);
    }
}
