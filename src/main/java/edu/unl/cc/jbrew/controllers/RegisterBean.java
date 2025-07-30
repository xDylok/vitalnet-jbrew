package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.domain.common.Person;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import edu.unl.cc.jbrew.bussiness.services.RoleRepository;
import edu.unl.cc.jbrew.bussiness.services.UserRepository;

import java.io.Serializable;
import java.util.List;


@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {
//Registro de usuarios.
    private String usuario;
    private String password;
    private String confirmpassword;

    private List<Role> availableRoles;

    @Email(message = "Correo electronico invalido")
    private String email;

    private Long selectedRoleId;

    @Inject
    private RoleRepository roleRepository;
    @Inject
    private UserRepository userRepository;




    @PostConstruct
    public void init() {
        availableRoles = roleRepository.buscarTodos();
    }

    public String register() {
        if (password == null || !password.equals(confirmpassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Las contraseñas no coinciden", null));
            return null;
        }

        Role selectedRole = roleRepository.buscarPorId(selectedRoleId);

        User user = new User();
        user.setName(usuario);
        Person person = new Person();
        person.setEmail(email);
        user.setPersona(person);
        user.setPassword(password); // ¡Recuerda cifrarla!
        user.setRole(selectedRole);

        userRepository.save(user);
        return "login.xhtml?faces-redirect=true";
    }

    //Getters y Setters

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

}