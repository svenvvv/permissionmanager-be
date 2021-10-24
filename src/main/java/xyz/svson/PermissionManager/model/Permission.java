package xyz.svson.PermissionManager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

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
