package com.Sercurity_service.repository;

import com.Sercurity_service.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    void deleteByEmail(String email);

    Optional<Otp> findByEmailAndOtpValueAndExpTimeFalse(String email, String otp);


}
