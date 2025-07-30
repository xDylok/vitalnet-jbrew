package edu.unl.cc.jbrew.domain.security;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permisos")
public class Permission implements java.io.Serializable {
//Entidad permiso.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resource;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    public Permission() {
    }

    public Permission(Long id, String resource, ActionType action) {
        this.id = id;
        this.resource = resource;
        this.action = action;
    }

    public boolean matchWith(String requestResource, ActionType requestAction) {
        return this.resource.equals(requestResource) &&
                this.action.equals(requestAction);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && Objects.equals(resource, that.resource) && action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resource, action);
    }
}
