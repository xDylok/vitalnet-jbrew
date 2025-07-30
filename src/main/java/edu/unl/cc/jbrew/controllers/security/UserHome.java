package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.common.Person;
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
import java.util.stream.Collectors;

//Bean para vista principal de usuario.
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


    @PostConstruct
    public void init() {
        availableRoles = new ArrayList<>(securityFacade.findAllRolesWithPermission());
    }

    private List<Role> availableRoles;

    public List<Role> getAvailableRoles() {
        return availableRoles.stream()
                .filter(r -> !r.getName().equalsIgnoreCase("paciente"))
                .collect(Collectors.toList());
    }

    public void setAvailableRoles(List<Role> availableRoles) {
        this.availableRoles = availableRoles;
    }

    public void loadUser() {
        logger.info("Loading user with id: " + selectedUserId);
        if (selectedUserId != null) {
            try {
                user = securityFacade.find(selectedUserId);
                if (user.getPerson() == null) {
                    user.setPerson(new Person());
                }
            } catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("No se pudo encontrar el usuario con id: " + selectedUserId);
            }
        } else {
            user = new User();
            user.setPerson(new Person()); // Asegura que nunca sea null
        }

        if (user.getRole() != null) {
            selectedRoleId = user.getRole().getId();
        }

        decryptPassword(user);
    }

    public UserHome() {
    }


    private void decryptPassword(User user) {
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                logger.info("Password no nulo y no vacio: " + user.getPassword());
                String pwdDecrypted = EncryptorManager.decrypt(user.getPassword());
                user.setPassword(pwdDecrypted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage(e.getMessage(), "Inconveniente al descifrar la clave: " + e.getMessage());
        }
    }

    public String create() {
        try {
            if (selectedRoleId != null) {
                Role selectedRole = securityFacade.getRoleById(selectedRoleId);
                user.setRole(selectedRole);
            }

            user = securityFacade.create(user);
            FacesUtil.addSuccessMessageAndKeep("Notificacion","Usuario creado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Notificacion","Inconveniente al crear usuario: " + e.getMessage());
            return null;
        }
    }

    public String update() {
        try {
            if (selectedRoleId != null) {
                Role selectedRole = securityFacade.getRoleById(selectedRoleId);
                user.setRole(selectedRole);
            }

            securityFacade.update(user);
            FacesUtil.addSuccessMessageAndKeep("Notificacion","Usuario actualizado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Notificacion","Inconveniente al actualizar usuario: " + e.getMessage());
            return null;
        }
    }

    public boolean isManaged() {
        return this.user != null && this.user.getId() != null;
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
    public Long getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Long selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }
}
