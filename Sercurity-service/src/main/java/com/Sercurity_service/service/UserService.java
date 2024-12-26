package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.response.UserResponse;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.enums.Role;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.mapper.UserMapper;
import com.Sercurity_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Users createUser(UserCreationRequest request){

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);

        Users user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
       // user.setRoles(roles);
        return userRepository.save(user);

    }

    public Users getUserById(String userID){

        return userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));

    }

    public List<UserResponse> getUsers(){
        List<Users> list = userRepository.findAll();

        return userMapper.toResponses(list);

    }

    public UserResponse getMyInfo(){
        UserResponse userResponse = new UserResponse();
        var authenticate = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticate.getName();
        userResponse = userMapper.userToResponse(userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        ));

        return userResponse;
    }
}
