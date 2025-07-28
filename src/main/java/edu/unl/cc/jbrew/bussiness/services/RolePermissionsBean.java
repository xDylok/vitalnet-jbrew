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

    public void loadPermissions() {
        Role role = roleRepository.findById(selectedRoleId);
        if (role != null) {
            List<Permission> all = permissionRepository.findAll();
            List<Permission> assigned = new ArrayList<>(role.getPermissions());
            List<Permission> available = all.stream()
                    .filter(p -> !assigned.contains(p))
                    .collect(Collectors.toList());
            dualPermissions = new DualListModel<>(available, assigned);
        }
    }

    public void savePermissions() {
        Role role = roleRepository.findById(selectedRoleId);
        if (role != null) {
            Set<Permission> newPermissions = new HashSet<>(dualPermissions.getTarget());
            role.setPermissions(newPermissions);
            roleRepository.save(role);
        }
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public PermissionRepository getPermissionRepository() {
        return permissionRepository;
    }

    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Long getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Long selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    public DualListModel<Permission> getDualPermissions() {
        return dualPermissions;
    }

    public void setDualPermissions(DualListModel<Permission> dualPermissions) {
        this.dualPermissions = dualPermissions;
    }

    public List<Role> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<Role> allRoles) {
        this.allRoles = allRoles;
    }
}
