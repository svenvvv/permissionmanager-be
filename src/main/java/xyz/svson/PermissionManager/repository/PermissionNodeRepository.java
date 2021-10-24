package xyz.svson.PermissionManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.svson.PermissionManager.model.PermissionNode;

public interface PermissionNodeRepository extends JpaRepository<PermissionNode, Long> {
}
