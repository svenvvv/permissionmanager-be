package xyz.svson.PermissionManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.svson.PermissionManager.model.Permission;
import xyz.svson.PermissionManager.repository.PermissionRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private Logger logger = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionRepository repository;

    PermissionController(PermissionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/available")
    public List<Permission> list() {
        return (List<Permission>) repository.findAll();
    }

    @PostMapping
    public Permission createPermission(@RequestBody Permission permission) {
        if (permission == null) {
            logger.warn("createPermission(): Empty request received");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing permission data");
        }

        if (permission.getTitle() == null) {
            logger.warn("createPermission(): Attempted to create permission without a title");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing title");
        }

        var existingPermission = repository.findByTitle(permission.getTitle());
        if (existingPermission.isPresent()) {
            logger.warn("createPermission(): Attempted to create permission with non-unique title");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non-unique title");
        }

        permission = repository.save(permission);
        return permission;
    }
}
