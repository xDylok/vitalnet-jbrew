package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Permission;
import edu.unl.cc.jbrew.domain.security.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleRepository {
//Acceso a datos de roles.
    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    public Role find(String name) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        List<Role> result = query.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Transactional
    public void assignPermissionToRole(Long roleId, String permissionId) {
        Role role = entityManager.find(Role.class, roleId);
        Permission permission = entityManager.find(Permission.class, permissionId);
        if (role != null && permission != null && !role.getPermissions().contains(permission)) {
            role.getPermissions().add(permission);
            entityManager.merge(role);
        }
    }

    public Role findByName(String name) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void removePermissionFromRole(Long roleId, String permissionId) {
        Role role = entityManager.find(Role.class, roleId);
        Permission permission = entityManager.find(Permission.class, permissionId);
        if (role != null && permission != null && role.getPermissions().contains(permission)) {
            role.getPermissions().remove(permission);
            entityManager.merge(role);
        }
    }

    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }


    public Set<Role> findAllWithPermissions() {
        List<Role> roles = entityManager.createQuery("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.permissions", Role.class)
                .getResultList();
        return roles.stream().collect(Collectors.toSet());
    }

    @Transactional
    public Role save(Role role) {
        if (role.getId() == null) {
            entityManager.persist(role);
            return role;
        } else {
            return entityManager.merge(role);
        }
    }

}
