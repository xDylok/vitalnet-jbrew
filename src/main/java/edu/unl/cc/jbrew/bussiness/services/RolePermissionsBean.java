package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Permission;
import edu.unl.cc.jbrew.domain.security.Role;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.DualListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class RolePermissionsBean implements Serializable {
// Bean JSF para asignar permisos a roles.

private static final long serialVersionUID = 1L;
    @Inject
    private RoleRepository roleRepository;
    @Inject
    private PermissionRepository permissionRepository;

    private Long selectedRoleId;
    private DualListModel<Permission> dualPermissions;
    private List<Role> allRoles;

    @PostConstruct
    public  void init() {
        allRoles = roleRepository.findAll();
        dualPermissions = new DualListModel<>();
    }

}
