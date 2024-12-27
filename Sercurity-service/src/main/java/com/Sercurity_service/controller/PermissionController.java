package com.Sercurity_service.controller;

import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.PermissionRequest;
import com.Sercurity_service.dto.response.PermissionResponse;
import com.Sercurity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @PostMapping("/create-permission")
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.createPermission(permissionRequest));
        return apiResponse;
    }
    @GetMapping("/permissions")
    ApiResponse<List<PermissionResponse>> getPermissions() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.getAllPermission());
        return apiResponse;
    }

}
