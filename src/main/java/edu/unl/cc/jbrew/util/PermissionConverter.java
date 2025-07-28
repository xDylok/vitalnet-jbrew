package edu.unl.cc.jbrew.util;

import edu.unl.cc.jbrew.bussiness.services.PermissionRepository;
import edu.unl.cc.jbrew.domain.security.Permission;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(value = "permissionConverter", forClass = Permission.class)
public class PermissionConverter implements Converter<Permission> {

    @Inject
    private PermissionRepository permissionRepository;

    @Override
    public Permission getAsObject(FacesContext facesContext, UIComponent uiComponent, String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        return permissionRepository.findById(Long.parseLong(valor));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Permission permission) {
        if (permission == null || permission.getId() == null) return "";
        return permission.getId().toString();
    }
}
