package com.lavertis.tasktrackerapi.services.user_service;

import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.Role;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.repositories.IUserRepository;
import com.lavertis.tasktrackerapi.services.role_service.IRoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {

    final private IUserRepository userRepository;
    final private IRoleService roleService;
    final private BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IRoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Long getAuthId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) throws NotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with requested id not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) throws BadRequestException {
        var usernameTaken = userRepository.existsByUsername(request.getUsername());
        if (usernameTaken)
            throw new BadRequestException("User with specified username already exists");

        var user = new User();
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);

        Role userRole = roleService.getRoleByName("USER");
        user.addRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public User changeUserPassword(Long id, String newPassword) throws NotFoundException {
        var encodedPassword = passwordEncoder.encode(newPassword);
        var user = findUserById(id);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User changeUserUsername(Long id, String newUsername) throws NotFoundException, BadRequestException {
        var usernameTaken = userRepository.existsByUsername(newUsername);
        if (usernameTaken)
            throw new BadRequestException("User with specified username already exists");

        var user = findUserById(id);
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
