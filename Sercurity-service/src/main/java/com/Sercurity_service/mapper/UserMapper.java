package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.request.UserUpdateRequest;
import com.Sercurity_service.dto.response.UserResponse;
import com.Sercurity_service.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "request.username", target = "username")
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "request.email", target = "email")
    @Mapping(source = "request.fullName", target = "fullName")
    @Mapping(source = "request.phone", target = "phone")
    @Mapping(source = "request.address", target = "address")
    Users toUser(UserCreationRequest request);

    @Mapping(source = "users.id", target = "id")
    @Mapping(source = "users.username", target = "username")
    @Mapping(source = "users.email", target = "email")
    @Mapping(source = "users.fullName", target = "fullName")
    @Mapping(source = "users.phone", target = "phone")
    @Mapping(source = "users.address", target = "address")
    @Mapping(source = "users.roles", target = "roles")
    UserResponse userToResponse(Users users);

    List<UserResponse> toResponses(List<Users> users);


}
