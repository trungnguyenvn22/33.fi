package com.Sercurity_service.repository;

import com.Sercurity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByRole(String roleName);
    List<Role> findAllByRoleIn(List<String> roles);
    List<Role> findAllByRole(String roleName);





}
