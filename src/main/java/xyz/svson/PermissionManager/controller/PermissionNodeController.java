package xyz.svson.PermissionManager.controller;

import org.springframework.web.bind.annotation.*;
import xyz.svson.PermissionManager.model.IdMessage;
import xyz.svson.PermissionManager.model.Permission;
import xyz.svson.PermissionManager.model.PermissionNode;
import xyz.svson.PermissionManager.model.ResponseMessage;
import xyz.svson.PermissionManager.repository.PermissionNodeRepository;
import xyz.svson.PermissionManager.repository.PermissionRepository;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class PermissionNodeController {
    private final PermissionNodeRepository repository;
    private final PermissionRepository permissionRepository;

    PermissionNodeController(PermissionNodeRepository repository, PermissionRepository permissionRepo) {
        this.repository = repository;
        this.permissionRepository = permissionRepo;
    }

    @GetMapping("/api/permissions/nodes")
    public List<PermissionNode> list() {
        return (List<PermissionNode>) repository.findAll();
    }

    @PostMapping(value = "/api/permissions/nodes", consumes = "application/json")
    public HashMap<String, Object> node_post(@RequestBody PermissionNode node) {
        HashMap<String, Object> ret = new HashMap<>();
        Optional<Permission> p = this.permissionRepository.findById(node.getPermissionId());
        if (!p.isPresent()) {
            return null;
        }

        node.setPermission(p.get());
        node = repository.save(node);

        ret.put("id", node.getId());
        return ret;
    }

    @PutMapping(value = "/api/permissions/nodes", consumes = "application/json")
    public HashMap<String, Object> node_put(@RequestBody PermissionNode node) {
        HashMap<String, Object> ret = new HashMap<>();
        Optional<PermissionNode> p = this.repository.findById(node.getId());
        if (!p.isPresent()) {
            return null;
        }

        node = this.repository.save(node);
        System.out.println(node);
        return ret;
    }

    @PutMapping(value = "/api/permissions/nodes/{id}/parent", consumes = "application/json")
    public HashMap<String, Object> nodeByIdPutParent(@PathVariable("id") Long id, @RequestBody IdMessage parentId) {
        HashMap<String, Object> ret = new HashMap<>();
        Optional<PermissionNode> child = this.repository.findById(id);
        if (!child.isPresent()) {
            return null;
        }

        if (parentId.getId() != null) {
            Optional<PermissionNode> parentNode = this.repository.findById(parentId.getId());
            if (!parentNode.isPresent()) {
                return null;
            }
            child.get().setParent(parentNode.get());
        } else {
            child.get().setParent(null);
        }

        this.repository.save(child.get());

        return ret;
    }

    @DeleteMapping(value = "/api/permissions/nodes/{id}")
    public ResponseMessage nodeByIdDelete(@PathVariable("id") Long id) {
        this.repository.deleteById(id);
        return new ResponseMessage("OK");
    }
}
