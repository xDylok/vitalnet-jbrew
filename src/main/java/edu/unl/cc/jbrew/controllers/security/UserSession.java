package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.security.ActionType;
import edu.unl.cc.jbrew.domain.security.Permission;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserSession implements java.io.Serializable{
//Manejo de sesión del usuario.
    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserSession.class.getName());

    @Inject
    SecurityFacade securityFacade;

    private User user;


    public void postLogin(@NotNull User user) throws EntityNotFoundException {
        logger.info("User logged in: " + user.getName());

        // Cargar usuario con su rol y permisos
        this.user = securityFacade.findUserWithRoleAndPermissions(user.getId());

        Set<Permission> permisos = (user.getRole() != null)
                ? user.getRole().getPermissions()
                : Collections.emptySet();

        logger.info("Permisos cargados para el usuario: " + permisos.size());
    }

    public User getUser() {
        return user;
    }



}
