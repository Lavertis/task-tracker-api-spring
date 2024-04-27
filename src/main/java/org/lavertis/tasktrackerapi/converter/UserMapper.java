package org.lavertis.tasktrackerapi.converter;

import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.dto.user.UserResponse;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", source = "username")
    UserResponse mapUserToUserResponse(AppUser user);

    @Mapping(target = "username", source = "email")
    AppUser mapCreateUserRequestToUser(CreateUserRequest createUserRequest);
}
