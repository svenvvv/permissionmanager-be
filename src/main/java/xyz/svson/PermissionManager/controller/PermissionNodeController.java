package xyz.svson.PermissionManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(PermissionNodeController.class);

    PermissionNodeController(PermissionNodeRepository repository, PermissionRepository permissionRepo) {
        this.repository = repository;
        this.permissionRepository = permissionRepo;
    }

    @GetMapping
    public List<PermissionNode> list() {
        return (List<PermissionNode>) repository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public IdMessage createNode(@RequestBody PermissionNode node) {
        if (node == null) {
            logger.warn("createNode(): Empty request received");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data provided");
        }
        if (node.getPermissionId() == null) {
            logger.warn("createNode(): Request without permission ID received");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing permission ID");
        }
        Optional<Permission> p = this.permissionRepository.findById(node.getPermissionId());
        if (!p.isPresent()) {
            logger.warn("createNode(): Attempted to create node with invalid permission ID");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such permission");
        }

        node.setPermission(p.get());
        node = repository.save(node);

        logger.debug("createNode(): Created new node (ID {}) for permission {}",
                     node.getId(), node.getPermissionId());

        return new IdMessage(node.getId());
    }

    @PutMapping(value = "/{id}/parent", consumes = "application/json")
    public void setNodeParent(@PathVariable("id") Long id, @RequestBody IdMessage parentId) {
        if (id == null) {
            logger.warn("setNodeParent(): Child ID missing");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Child ID missing");
        }
        if (parentId == null) {
            logger.warn("setNodeParent(): Parent ID missing");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent ID missing");
        }

        Optional<PermissionNode> child = this.repository.findById(id);
        if (!child.isPresent()) {
            logger.warn("setNodeParent(): Attempted to adopt non-existent node");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Child does not exist");
        }

        if (parentId.getId() != null) {
            Optional<PermissionNode> parentNode = this.repository.findById(parentId.getId());
            if (!parentNode.isPresent()) {
                logger.warn("setNodeParent(): Attempted to adopt to non-existent parent");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent does not exist");
            }
            child.get().setParent(parentNode.get());
        } else {
            child.get().setParent(null);
        }

        logger.debug("setNodeParent(): Adopted child (ID {}) to parent (ID {})",
                     child.get().getId(), child.get().getParent().getId());

        this.repository.save(child.get());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteNode(@PathVariable("id") Long id) {
        try {
            this.repository.deleteById(id);
            logger.debug("deleteNode(): Deleted node (ID {})", id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("deleteNode(): Attempted to delete non-existent ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node does not exist");
        }
    }
}
