package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.*;

/**
 *
 * @author wduck
 */
@ApplicationScoped
public class UserRepository {

    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    @Transactional
    public User save(User user){
        if (user.getId() == null){
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
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

    public User findUserWithRoles(Long userId) throws EntityNotFoundException {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id", User.class)
                .setParameter("id", userId)
                .getResultList();

        if(users.isEmpty()){
            throw new EntityNotFoundException("User no encontrado con ID: " + userId);
        }
        return users.get(0);
    }

}
