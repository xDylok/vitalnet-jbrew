package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Permission;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class PermissionRepository {

    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    public List<Permission> findAll() {
        return  entityManager.createQuery("SELECT p FROM Permission p", Permission.class).getResultList();
    }

    public Permission findById(Long id) {
        return entityManager.find(Permission.class, id);
    }

    public Permission save(Permission permission) {
        if (permission.getId() == null) {
            entityManager.persist(permission);
            return permission;
        } else {
            return entityManager.merge(permission);
        }
    }
}
