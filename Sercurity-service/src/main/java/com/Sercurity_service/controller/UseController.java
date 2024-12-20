package com.Sercurity_service.controller;

import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.response.UserResponse;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UseController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ApiResponse<Users> createUser(@RequestBody UserCreationRequest request) {

        ApiResponse<Users>  apiUser = new ApiResponse<>();
        apiUser.setCode(200);
        apiUser.setResult(userService.createUser(request));
        return apiUser;
    };

    @GetMapping("/get-user/{id}")
    Users getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    };

    @GetMapping("/get-users")
    ApiResponse<List<UserResponse>> getUsers(){
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getUsers());
        return response;

    }

}

