package xyz.svson.PermissionManager.model;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;

    private Object data;

    private ResponseMessage() {}

    public ResponseMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(String message) {
        this.message = message;
    }
}
