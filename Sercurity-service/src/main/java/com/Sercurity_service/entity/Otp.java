package com.Sercurity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Otp {
    @Id
    long id;
    String otpValue;
    String email;
    LocalDateTime expTime;
    boolean used;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expTime);
    }
}
