package xyz.svson.PermissionManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.svson.PermissionManager.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
