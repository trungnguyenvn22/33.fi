package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.PermissionRequest;
import com.Sercurity_service.dto.response.PermissionResponse;
import com.Sercurity_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Mapping(source = "permissionRequest.permission", target = "permission")
    @Mapping(source = "permissionRequest.description", target = "description")
    Permission toPermission(PermissionRequest permissionRequest);

    @Mapping(source = "permission.permission", target = "permission")
    @Mapping(source = "permission.description", target ="description")
    PermissionResponse permissionToResponse(Permission permission);

    List<PermissionResponse> toListPermissionResponses(List<Permission> list);

}
