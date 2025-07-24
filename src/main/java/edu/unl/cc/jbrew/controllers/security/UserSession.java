package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.security.ActionType;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserSession implements java.io.Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserSession.class.getName());

    @Inject
    SecurityFacade securityFacade;

    private User user;

    public void postLogin(@NotNull User user) throws EntityNotFoundException {
        logger.info("User logged in: " + user.getName());

        this.user = user;

        Set<Role> roles = securityFacade.findRolesWithPermission(this.user.getId());

        if (roles == null) {
            roles = Collections.emptySet();
        } else {
            roles.removeIf(role -> role == null || role.getName() == null);
        }

        this.user.setRoles(roles);


        logger.info("Roles asignados al usuario: " + roles.size());
    }


    public boolean hasPermissionForPage(String pagePath) {
        return this.hasPermission(pagePath, ActionType.READ);
    }

    public boolean hasPermission(String resource, ActionType action) {
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.matchWith(resource, action));
    }

    public boolean hasRole(@NotNull String roleName) {
        if (user == null || user.getRoles() == null) {
            return false;
        }

        return user.getRoles().stream()
                .filter(Objects::nonNull)
                .filter(role -> role.getName() != null)
                .anyMatch(role -> role.getName().equals(roleName));
    }



    public User getUser() {
        return user;
    }
}
