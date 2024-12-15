package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.entity.Users;
import jakarta.validation.constraints.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "request.username", target = "username")
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "request.email", target = "email")
    @Mapping(source = "request.fullName", target = "fullName")
    @Mapping(source = "request.phone", target = "phone")
    @Mapping(source = "request.address", target = "address")
    Users toUser(UserCreationRequest request);
}
