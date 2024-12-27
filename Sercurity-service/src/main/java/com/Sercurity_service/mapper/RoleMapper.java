package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.RoleRequest;
import com.Sercurity_service.dto.response.RoleResponse;
import com.Sercurity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "request.role", target = "role")
    @Mapping(source = "request.description", target = "description")
    @Mapping(target = "permissions", ignore = true)
    Role requestToRole(RoleRequest request);

    @Mapping(source = "role.role", target = "role")
    @Mapping(source = "role.description", target = "description")
    @Mapping(source = "role.permissions", target = "permissions")
    RoleResponse toRoleResponse(Role role);

    List<RoleResponse> toListRoleResponse(List<Role> roles);
}
