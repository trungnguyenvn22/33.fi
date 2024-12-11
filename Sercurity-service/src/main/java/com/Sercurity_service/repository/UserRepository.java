package com.Sercurity_service.repository;

import com.Sercurity_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);
}
