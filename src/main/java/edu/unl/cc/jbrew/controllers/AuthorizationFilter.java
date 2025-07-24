package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.controllers.security.UserPrincipal;
import edu.unl.cc.jbrew.controllers.security.UserSession;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.faces.FacesUtil;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter("*.xhtml")
public class AuthorizationFilter implements Filter {

    @Inject
    UserSession userSession;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        String requestPath = httpReq.getServletPath();

        // 1. Permitir recursos públicos
        if (requestPath.startsWith("/public/") || requestPath.equals("/login.xhtml") || requestPath.equals("/index.xhtml")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 2. Obtener usuario autenticado
        //UserPrincipal user = FacesUtil.getCurrentUser();
        User user = userSession.getUser();

        // 3. Redirigir si no está autenticado
        if (user == null) {
            ((HttpServletResponse) servletResponse).sendRedirect(httpReq.getContextPath() + "/login.xhtml");
            return;
        }

        // 4. Verificar permisos para la página solicitada
        if (userSession.hasPermissionForPage(requestPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
        }
    }
}
