package com.Sercurity_service.service;


import com.Sercurity_service.dto.request.RoleRequest;
import com.Sercurity_service.dto.response.PermissionResponse;
import com.Sercurity_service.dto.response.RoleResponse;
import com.Sercurity_service.entity.Permission;
import com.Sercurity_service.entity.Role;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.mapper.RoleMapper;
import com.Sercurity_service.repository.PermissionRepository;
import com.Sercurity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.HashSet;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest roleRequest) {
        if (roleRepository.existsByRole(roleRequest.getRole()))
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);
        var role = roleMapper.requestToRole(roleRequest);
        var permission = permissionRepository.findAllByPermissionIn(roleRequest.getPermissions());
        HashSet<Permission> setPermission = new HashSet<>(permission);
        role.setPermissions(setPermission);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        List< Role> list = roleRepository.findAll();
        return roleMapper.toListRoleResponse(list);
    }
}
