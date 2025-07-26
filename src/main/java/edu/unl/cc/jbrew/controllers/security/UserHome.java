package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import edu.unl.cc.jbrew.faces.FacesUtil;
import edu.unl.cc.jbrew.util.EncryptorManager;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserHome implements java.io.Serializable{

    private static Logger logger = Logger.getLogger(UserHome.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private Long selectedUserId;

    private User user;

    @Inject
    SecurityFacade securityFacade;

    private Long selectedRoleId;
    private List<Role> availableRoles;

    @PostConstruct
    public void init() {
        availableRoles = new ArrayList<>(securityFacade.findAllRolesWithPermission());
    }

    public UserHome() {
    }

    public void loadUser() {
        logger.info("Loading user with id: " + selectedUserId);
        if (selectedUserId != null) {
            try {
                user = securityFacade.find(selectedUserId);
                if (user.getPerson() == null) {
                   user.setPerson(new edu.unl.cc.jbrew.domain.common.Person());
                }
            } catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("No se pudo encontrar el usuario con id: " + selectedUserId);
            }
        } else {
            user = new User();
        }
        decryptPassword(user);
    }

    private void decryptPassword(User user){
        String pwdDecrypted = null;
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()){
                logger.info("Password no nulo y no vacio: " + user.getPassword());
                pwdDecrypted = EncryptorManager.decrypt(user.getPassword());
                user.setPassword(pwdDecrypted);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage(e.getMessage(), "Invconveniente al decifrar la clave: " + e.getMessage());
        }

    }

    public String create() {
        try {
            Role selectedRole = securityFacade.getRoleById(selectedRoleId);
            user.setRole(selectedRole);
            user = securityFacade.create(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario creado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al crear usuario: " + e.getMessage());
            return null;
        }
    }

    public String update() {
        try {
            Role selectedRole = securityFacade.getRoleById(selectedRoleId);
            user.setRole(selectedRole);
            securityFacade.update(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario actuaalizado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar usuario: " + e.getMessage());
            return null;
        }
    }
    public Long getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Long selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    public List<Role> getAvailableRoles() {
        return availableRoles;
    }
    public boolean isManaged(){
        return this.user.getId() != null;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
