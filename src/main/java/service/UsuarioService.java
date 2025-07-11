package edu.unl.cc.jbrew.vitalnet.service;

import edu.unl.cc.jbrew.vitalnet.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @PersistenceContext(unitName = "vitalnetPU")
    private EntityManager entityManager;

    public void save(Usuario usuario) {
        if (usuario.getId() == null) {
            entityManager.persist(usuario);
        } else {
            entityManager.merge(usuario);
        }
    }

    public void delete(Long id) {
        Usuario u = entityManager.find(Usuario.class, id);
        if (u != null) {
            entityManager.remove(u);
        }
    }

    public List<Usuario> obtenerTodos() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
}

