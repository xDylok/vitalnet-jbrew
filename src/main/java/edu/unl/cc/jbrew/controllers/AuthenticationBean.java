package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.controllers.security.UserPrincipal;
import edu.unl.cc.jbrew.controllers.security.UserSession;
import edu.unl.cc.jbrew.domain.security.User;
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

    //@Inject
    //private Logger logger;

    @NotNull
    private String username;
    @NotNull
    @Size(min=8, message="Contraseña muy corta")
    private String password;

    @Inject
    private SecurityFacade securityFacade;

    @Inject
    private UserSession userSession;

    //@Inject
    //private FacesContext facesContext;

    public String login(){
        logger.info("Logging in with username: " + username);
        logger.info("Logging in with password: " + password);
        try {
            User user = securityFacade.authenticate(username, password);
            setHttpSession(user);

            FacesUtil.addMessageAndKeep(FacesMessage.SEVERITY_INFO, "Aviso", "Bienvenido " + user.getName() + " a la web VitalNet.");
            //FacesMessage fc = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", "Bienvenido " + user.getName() + " a la aplicación Jbrew.");
            //facesContext.getExternalContext().getFlash().setKeepMessages(true);

            System.out.println("--------------> userSession Longin: " + userSession.getUser());
            userSession.postLogin(user);
            return "indexLogin.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR,  e.getMessage(), null);
            return null;
        }
    }

    public String logout() throws ServletException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        FacesUtil.addSuccessMessageAndKeep("Logged out successfully");
        //FacesMessage fc = new FacesMessage("Logged out successfully");
        //facesContext.addMessage(null, fc);
        //facesContext.getExternalContext().getFlash().setKeepMessages(true);

        ((jakarta.servlet.http.HttpServletRequest) facesContext.getExternalContext().getRequest()).logout();
        //userSession.logout();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean verifyUserSession(){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionMap().containsKey("user");
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
