package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.controllers.security.UserPrincipal;
import edu.unl.cc.jbrew.controllers.security.UserSession;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.CredentialInvalidException;
import edu.unl.cc.jbrew.faces.FacesUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.logging.Logger;

@Named
@ViewScoped
public class AuthenticationBean implements java.io.Serializable{
// Bean para inicio de sesión.
    private static Logger logger = Logger.getLogger(AuthenticationBean.class.getName());

    @NotNull
    private String username;
    @NotNull
    @Size(min=8, message="Contraseña muy corta")
    private String password;

    @Inject
    private SecurityFacade securityFacade;

    @Inject
    private UserSession userSession;

    public String login() {
        logger.info("Logging in with username: " + username);
        try {
            User user = securityFacade.authenticate(username, password);
            setHttpSession(user);

            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Bienvenido " + user.getName() + " a la web VitalNet."));

            userSession.postLogin(user);
            return "menu.xhtml?faces-redirect=true";
        } catch (CredentialInvalidException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de autenticación", e.getMessage()));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
            return null;
        }
    }


    public String logout() throws ServletException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        FacesUtil.addSuccessMessageAndKeep("Aviso", "Sesión cerrada correctamente");
        ((jakarta.servlet.http.HttpServletRequest) facesContext.getExternalContext().getRequest()).logout();
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Establece la sessión de usuario
     * @param user
     */
    private void setHttpSession(User user){
        FacesContext context = FacesContext.getCurrentInstance();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        context.getExternalContext().getSessionMap().put("user", userPrincipal);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
