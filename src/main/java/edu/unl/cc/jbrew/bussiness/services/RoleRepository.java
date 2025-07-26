package edu.unl.cc.jbrew.bussiness.services;

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

    /*
    public Role findRoleWithPermissionByUserId(Long userId, String permissionName) {
        String jpql = "SELECT r FROM Role r JOIN r.permissions p WHERE r = (SELECT u.role FROM User u WHERE u.id = :userId) AND p.name = :permissionName";
        try {
            return entityManager.createQuery(jpql, Role.class)
                    .setParameter("userId", userId)
                    .setParameter("permissionName", permissionName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // o maneja la excepción como quieras
        }
    }*/

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
/*
    public Set<Role> findRolesByUserId(Long userId) {
        try {

            // Consulta corregida
            TypedQuery<Role> query = entityManager.createQuery(
                    "SELECT r FROM User u, Role r WHERE u.role.id = r.id AND u.id = :userId",
                    Role.class
            );
            query.setParameter("userId", userId);
            Role role = query.getSingleResult();
            return role != null ? Collections.singleton(role) : Collections.emptySet();
        } catch (NoResultException e) {
            return Collections.emptySet();
        }
    }


*/
}
