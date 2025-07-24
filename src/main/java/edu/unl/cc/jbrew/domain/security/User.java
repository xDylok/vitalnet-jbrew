package edu.unl.cc.jbrew.domain.security;

import edu.unl.cc.jbrew.domain.common.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull @NotEmpty
    @Column(nullable = false)
    private String password;

    // Relations
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public User() {
        person = new Person();
    }

    /**
     *
     * @param id
     * @param name
     * @param password
     * @throws IllegalArgumentException
     */
    public User(Long id, String name, String password) throws IllegalArgumentException{
        this();
        this.id = id;
        validateNameRestriction(name);
        this.name = name.trim();
        this.password = password;
    }

    /**
     *
     * @param id
     * @param name
     * @param password
     * @param person
     * @throws IllegalArgumentException
     */
    public User(Long id, String name, String password, Person person)
            throws IllegalArgumentException{
        this(id, name, password);
        this.person = person;
    }

    /**
     *
     * @param text
     * @throws IllegalArgumentException
     */
    private void validateNameRestriction(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor reequerido para name");
        }

        if (text.trim().length() < 5){
            throw new IllegalArgumentException("Valor para name debe supera los 5 caracteres");
        }
    }


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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "User{" + "id:" + id + ", name:" + name + ", password:" + password
                + ", person{" + person + '}' + '}';
    }
}