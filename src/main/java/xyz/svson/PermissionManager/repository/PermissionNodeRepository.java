package xyz.svson.PermissionManager.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.svson.PermissionManager.model.PermissionNode;

public interface PermissionNodeRepository extends CrudRepository<PermissionNode, Long> {
}
