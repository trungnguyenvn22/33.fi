package com.Sercurity_service.controller;


import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.RoleRequest;
import com.Sercurity_service.dto.response.RoleResponse;
import com.Sercurity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/create-role")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.createRole(roleRequest));
        return response;
    }
    @GetMapping("/roles")
    ApiResponse<List<RoleResponse>> getAllRoles(){
        ApiResponse<List<RoleResponse>> response = new ApiResponse<>();
        response.setResult(roleService.getAll());
        return response;
    }

    @PostMapping("/update-role")
    ApiResponse<RoleResponse> updateRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.updateRole(roleRequest));
        return response;
    }
}
