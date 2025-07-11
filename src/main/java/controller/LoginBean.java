package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.model.User;
import edu.unl.cc.jbrew.vitalnet.model.Usuario;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private User usuarioLogueado;

    // Simulador de autenticación
    public void login() {
        if ("admin".equals(username) && "1234".equals(password)) {
            usuarioLogueado = new User(username, "Administrador"); // Crea usuario simulado
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("pacientes.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login incorrecto", "Usuario o contraseña inválidos"));
        }
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters y setters

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User getUsuarioLogueado() { return usuarioLogueado; }
    public void setUsuarioLogueado(User usuarioLogueado) { this.usuarioLogueado = usuarioLogueado; }
}

