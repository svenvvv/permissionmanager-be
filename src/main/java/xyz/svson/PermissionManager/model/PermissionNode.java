package xyz.svson.PermissionManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class PermissionNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "permission_id")
    @JsonIgnore
    private Permission permission;

    @Column(name = "permission_id", insertable = false, updatable = false)
    private Long permissionId;

    @ManyToOne(fetch = FetchType.EAGER)
    private PermissionNode parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<PermissionNode> children;

    private PermissionNode() { }

    public PermissionNode(Permission permission, PermissionNode parent, Set<PermissionNode> children) {
        this.permission = permission;
        this.parent = parent;
        this.children = children;
    }
}
