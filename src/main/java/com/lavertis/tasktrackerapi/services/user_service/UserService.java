package com.lavertis.tasktrackerapi.services.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.exceptions.BadRequestException;
import com.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import com.lavertis.tasktrackerapi.exceptions.NotFoundException;
import com.lavertis.tasktrackerapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;
    final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private User checkRequestUser(Long requestedUserId) throws NotFoundException, ForbiddenRequestException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = findUserByUsername(username);
        if (user.getId().equals(requestedUserId))
            throw new ForbiddenRequestException("Id of the request principal does not match passed user id");
        return user;
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
    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with requested username not found"));
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
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException, ForbiddenRequestException {
        var user = checkRequestUser(id);
        User userPatched = applyPatchToUser(patch, user);
        userRepository.save(userPatched);
        return userPatched;
    }

    private User applyPatchToUser(JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

    @Override
    public void deleteUserById(Long id) throws NotFoundException, ForbiddenRequestException {
        checkRequestUser(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
