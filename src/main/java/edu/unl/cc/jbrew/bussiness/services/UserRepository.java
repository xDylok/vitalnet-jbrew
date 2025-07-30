package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Permission;
import edu.unl.cc.jbrew.domain.security.Role;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import jakarta.transaction.Transactional;

import java.util.*;


@ApplicationScoped
public class UserRepository {
//Acceso a datos de usuarios.
    //Define la conexion de la aplicacion a la base da datos
    @PersistenceContext(unitName = "vitaldb")
    private EntityManager entityManager;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @Transactional
    public User save(User user){
        if (user.getId() == null){
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public User find(Long id) throws EntityNotFoundException {
        User user = entityManager.find(User.class, id);
        if (user == null){
            throw new EntityNotFoundException("User no encontrado con [" + id + "]");
        }
        return user;
    }

    public User find(String name) throws EntityNotFoundException {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            throw new EntityNotFoundException("User no encontrado con nombre: " + name);
        }
    }


    public List<User> findWithLike(String criteria) throws EntityNotFoundException {
        List<User> results = entityManager.createQuery(
                        "SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(:criteria)", User.class)
                .setParameter("criteria", "%" + criteria + "%")
                .getResultList();

        if (results.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron usuarios con criterio: " + criteria);
        }
        return results;
    }

    public User findUserWithRoleAndPermissions(Long userId) {
        String jpql = "SELECT u FROM User u, Role r " +
                "WHERE u.role.id = r.id AND u.id = :userId";

        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public User findByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

}