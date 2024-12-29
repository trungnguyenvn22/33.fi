package com.Sercurity_service.controller;

import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.request.UserUpdateRequest;
import com.Sercurity_service.dto.response.UserResponse;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.repository.UserRepository;
import com.Sercurity_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UseController {
    private static final Logger log = LoggerFactory.getLogger(UseController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    ApiResponse<Users> createUser(@RequestBody UserCreationRequest request) {

        ApiResponse<Users>  apiUser = new ApiResponse<>();
        apiUser.setCode(200);
        apiUser.setResult(userService.createUser(request));
        return apiUser;
    };

    @PostAuthorize("hasRole('USER')")
    @GetMapping("/get-user/{id}")
    Users getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    };

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-users")
    ApiResponse<List<UserResponse>> getUsers(){
        log.info("in method get user");
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}",authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info("Authority: {}", authority));

        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getUsers());
        return response;

    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("hasRole('ADMIN')")
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getMyInfo());
        return response;
    }

    @PostMapping("/update")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.updateUser(request));
        return response;
    }



}

