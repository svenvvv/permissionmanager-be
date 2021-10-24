package xyz.svson.PermissionManager.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.svson.PermissionManager.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
