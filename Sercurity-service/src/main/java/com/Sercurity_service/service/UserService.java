package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.UserCreationRequest;
import com.Sercurity_service.dto.request.UserUpdateRequest;
import com.Sercurity_service.dto.response.UserResponse;
import com.Sercurity_service.entity.Role;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.mapper.UserMapper;
import com.Sercurity_service.mapper.UserUpdateMapper;
import com.Sercurity_service.repository.RoleRepository;
import com.Sercurity_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository repository;

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserUpdateMapper userUpdateMapper;

    final String user_role = "USER";



    public Users createUser(UserCreationRequest request){

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);

        Users user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = roleRepository.findAllByRole(user_role);
        user.setRoles(new HashSet<>(roles));
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


    public UserResponse updateUser(UserUpdateRequest request){

        log.info("user id: " + request.getId());
        Users user = userRepository.findById(request.getId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        List<Role> roles = roleRepository.findAllByRoleIn(request.getRoles());
        log.info("user info: " + user.toString());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return userMapper.userToResponse(user);

    }
}
