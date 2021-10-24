package xyz.svson.PermissionManager.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "permission")
@Data
public class Permission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 3) @Column(unique = true)
    private String title;

    private String subtitle;

    protected Permission() {}

    public Permission(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
