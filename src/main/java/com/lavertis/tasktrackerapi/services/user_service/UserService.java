package com.lavertis.tasktrackerapi.services.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lavertis.tasktrackerapi.dto.CreateUserRequest;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class UserService implements IUserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with requested id not found"));
    }

    @Override
    public User create(CreateUserRequest request) {
        var user = new User();
        var encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateById(long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with requested id not found"));

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
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
