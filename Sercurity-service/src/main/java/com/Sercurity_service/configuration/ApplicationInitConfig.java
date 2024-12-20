package com.Sercurity_service.configuration;

import com.Sercurity_service.entity.Users;
import com.Sercurity_service.enums.Role;
import com.Sercurity_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    ApplicationRunner applicationRunner(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                Users users = new Users();
                users.setUsername("admin");
                users.setPassword(passwordEncoder.encode("admin"));
                users.setRoles(roles);
                userRepository.save(users);
                log.warn("admin user has been created with default username and password");
            }
        };
    };
}
