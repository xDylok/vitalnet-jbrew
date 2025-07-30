package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.controllers.AuthenticationBean;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserList implements java.io.Serializable{
//Bean para listar usuarios.
    private static Logger logger = Logger.getLogger(UserList.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private String criteria;
    private List<User> users;

    @Inject
    SecurityFacade securityFacade;

    public UserList() {
        users = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        logger.info("****** POST CONSTRUCTOR: " + getCriteria() + " ******");
        try {
            this.search();
        } catch (Exception e) {
            logger.warning("Error en init(): " + e.getMessage());
            users.clear();
        }
    }
    public void delete(User user) {
        try {
            securityFacade.deleteUser(user);
            users.remove(user); // actualizar lista en memoria
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado", "El usuario ha sido eliminado correctamente."));
            logger.info("Usuario eliminado: " + user.getName());
        } catch (Exception e) {
            logger.severe("Error al eliminar usuario: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el usuario: " + e.getMessage()));
        }
    }

    public void search()  {
        try {
            logger.info("****** Ingreso a buscar con: " + getCriteriaBuffer() + " ******");
            users = securityFacade.findUsers(getCriteriaBuffer());
        } catch (EntityNotFoundException e) {
            users.clear();
        }
    }

    public String edit(User _selected){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selected", _selected);
        return "userEdit?faces-redirect=true&id=" + _selected.getId();
    }



    public void reset() {
        criteria = null;
        users.clear();
    }

    private String getCriteriaBuffer(){
        return criteria != null && !criteria.isEmpty()? criteria : "%";
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
