package com.Sercurity_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "Users")
public class Users {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     String id;
     @Size(min = 6, message = "USERNAME_INVALID")
     String username;
     @Size(min = 8,message = "")
     String password;
     String email;
     String fullName;
     String phone;
     String address;

     @ManyToMany
     Set<Role> roles;



}
