package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.request.UserUpdateRequest;
import com.Sercurity_service.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

//    @Mapping(source = "request.id", target = "id")
//    @Mapping(source = "request.username", target = "username")
//    @Mapping(target = "password", ignore = true)
//    @Mapping(source = "request.email", target = "email")
//    @Mapping(source = "request.fullName", target = "fullName")
//    @Mapping(source = "request.phone", target = "phone")
//    @Mapping(source = "request.address", target = "address")
//    @Mapping(target = "roles", ignore = true)
    Users toUser(UserCreationRequest request);
    Users updateToUser(UserUpdateRequest request);
}
