package edu.unl.cc.jbrew.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;

import java.io.Serializable;

@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {

    private String usuario;
    @Email(message = "Correo electronico invalido")
    private String email;
    private String password;
    private String confirmpassword;

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

    // Acción del boton siguiente
    public String registrar() {
        if (password == null || !password.equals(confirmpassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Las contraseñas no coinciden", null));
            return null;
        }

        return "registroExitoso?faces-redirect=true";
    }
}