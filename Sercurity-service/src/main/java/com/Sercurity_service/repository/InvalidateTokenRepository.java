package com.Sercurity_service.repository;

import com.Sercurity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidateTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsAllByToken(String token);
}
