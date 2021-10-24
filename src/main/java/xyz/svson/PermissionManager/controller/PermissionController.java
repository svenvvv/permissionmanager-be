package xyz.svson.PermissionManager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.svson.PermissionManager.model.Permission;
import xyz.svson.PermissionManager.repository.PermissionRepository;

import java.util.List;

@RestController
public class PermissionController {
    private final PermissionRepository repository;

    PermissionController(PermissionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/permissions/available")
    public List<Permission> list() {
        return (List<Permission>) repository.findAll();
    }
}
