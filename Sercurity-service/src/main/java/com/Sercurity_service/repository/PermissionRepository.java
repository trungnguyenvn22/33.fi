package com.Sercurity_service.repository;

import com.Sercurity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findPermissionByPermission(String permission);
    boolean existsByPermission(String permission);

}
