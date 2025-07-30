package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.domain.security.ActionType;
import edu.unl.cc.jbrew.domain.security.User;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.security.Principal;

public class UserPrincipal implements Principal, Serializable {
//Representación del usuario autenticado.
    private final User user;

    public UserPrincipal(@NotNull User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
