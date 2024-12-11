package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users createUser(UserCreationRequest request){
        Users user = new Users();

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);

        

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        return userRepository.save(user);

    }

    public Users getUserById(String userID){

        return userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));

    }
}
