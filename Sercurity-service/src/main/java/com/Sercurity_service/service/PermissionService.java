package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.PermissionRequest;
import com.Sercurity_service.dto.response.PermissionResponse;
import com.Sercurity_service.entity.Permission;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.mapper.PermissionMapper;
import com.Sercurity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        if(permissionRepository.existsByPermission(request.getPermission()))
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);


        permission.setPermission(request.getPermission());
        permission.setDescription(request.getDescription());
        permissionRepository.save(permission);
        return permissionMapper.permissionToResponse(permission);
    }

    public List<PermissionResponse> getAllPermission(){
        List<Permission> list = permissionRepository.findAll();

        return permissionMapper.toListPermissionResponses(list);
    }



}
