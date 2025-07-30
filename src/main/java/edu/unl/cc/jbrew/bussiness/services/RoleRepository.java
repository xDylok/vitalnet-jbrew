package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleRepository {
//Acceso a datos de roles.
    @PersistenceContext(unitName = "vitaldb")
    private EntityManager entityManager;

    public Role buscarPorId(Long id) {
        return entityManager.find(Role.class, id);
    }


    public Role buscarPorNombre(String name) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public List<Role> buscarTodos() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }


    public Set<Role> buscarTodosConPermisos() {
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
