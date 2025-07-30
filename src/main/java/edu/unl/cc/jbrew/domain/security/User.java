package edu.unl.cc.jbrew.domain.security;

import edu.unl.cc.jbrew.domain.common.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull @NotEmpty
    @Column(nullable = false)
    private String password;

    // Relación con persona
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Person persona;

    // Relación con un único rol
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("resource ASC")
    @JoinTable(
            name = "permiso_role",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_permisos")
    )
    private Set<Permission> permissions = new HashSet<>();

    // Constructor por defecto
    public User() {
        this.persona = new Person();
    }

    // Constructor con validación
    public User(Long id, String name, String password) {
        this();
        this.id = id;
        validateNameRestriction(name);
        this.name = name.trim();
        this.password = password;
    }

    public User(Long id, String name, String password, Person persona) {
        this(id, name, password);
        this.persona = persona;
    }

    // Validación de nombre
    private void validateNameRestriction(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Valor requerido para name");
        }
        if (text.trim().length() < 5) {
            throw new IllegalArgumentException("El nombre debe tener al menos 5 caracteres");
        }
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPersona() {
        return persona;
    }

    public void setPersona(Person persona) {
        this.persona = persona;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // equals, hashCode y toString

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User other)) return false;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', role=" + (role != null ? role.getName() : "N/A") + '}';
    }
}
