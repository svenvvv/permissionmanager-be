package xyz.svson.PermissionManager.model;

import lombok.Data;

@Data
public class IdMessage {
    private Long id;

    private IdMessage() {}

    public IdMessage(Long id) {
        this.id = id;
    }
}
