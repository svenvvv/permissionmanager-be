package xyz.svson.PermissionManager.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.svson.PermissionManager.model.IdMessage;
import xyz.svson.PermissionManager.model.Permission;
import xyz.svson.PermissionManager.model.PermissionNode;
import xyz.svson.PermissionManager.repository.PermissionNodeRepository;
import xyz.svson.PermissionManager.repository.PermissionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions/nodes")
public class PermissionNodeController {
    private final PermissionNodeRepository repository;
    private final PermissionRepository permissionRepository;

    PermissionNodeController(PermissionNodeRepository repository, PermissionRepository permissionRepo) {
        this.repository = repository;
        this.permissionRepository = permissionRepo;
    }

    @GetMapping
    public List<PermissionNode> list() {
        return (List<PermissionNode>) repository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public IdMessage nodePost(@RequestBody PermissionNode node) {
        Optional<Permission> p = this.permissionRepository.findById(node.getPermissionId());
        if (!p.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such permission");
        }

        node.setPermission(p.get());
        node = repository.save(node);

        return new IdMessage(node.getId());
    }

    @PutMapping(value = "/{id}/parent", consumes = "application/json")
    public void nodeByIdPutParent(@PathVariable("id") Long id, @RequestBody IdMessage parentId) {
        Optional<PermissionNode> child = this.repository.findById(id);
        if (!child.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Child does not exist");
        }

        if (parentId.getId() != null) {
            Optional<PermissionNode> parentNode = this.repository.findById(parentId.getId());
            if (!parentNode.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent does not exist");
            }
            child.get().setParent(parentNode.get());
        } else {
            child.get().setParent(null);
        }

        this.repository.save(child.get());
    }

    @DeleteMapping(value = "/{id}")
    public void nodeByIdDelete(@PathVariable("id") Long id) {
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node does not exist");
        }
    }
}
