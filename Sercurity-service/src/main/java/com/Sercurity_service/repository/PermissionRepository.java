package com.Sercurity_service.repository;

import com.Sercurity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findPermissionByPermission(String permission);
    boolean existsByPermission(String permission);
    List<Permission> findAllByPermissionIn (Set<String> permission);

    @Query("select p from Permission  p where  p.permission IN:permissions")
    List<Permission> getPermissionByRolePermission(@Param("permissions") Set<String> permissions);


}
