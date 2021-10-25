package xyz.svson.PermissionManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.svson.PermissionManager.model.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    public Optional<Permission> findByTitle(String title);
}
