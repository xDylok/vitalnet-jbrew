package edu.unl.cc.jbrew.vitalnet.service;

import edu.unl.cc.jbrew.vitalnet.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class PacienteService {
    @PersistenceContext(unitName = "vitalnetPU")
    private EntityManager entityManager;

    public void Save(Paciente paciente) {
        if (paciente.getId() == null) {
            entityManager.persist(paciente);
        } else {
            entityManager.merge(paciente);
        }
    }

    public void Delete(Long id) {
        Paciente paciente = entityManager.find(Paciente.class, id);
        if (paciente != null) {
            entityManager.remove(paciente);
        }
    }

    public List<Paciente> obtenerTodos() {
        return entityManager.createQuery("SELECT p FROM Paciente p", Paciente.class).getResultList();
    }

    public List<Paciente> buscarPorCedula(String cedula) {
        TypedQuery<Paciente> query = entityManager.createQuery("SELECT p FROM Paciente p WHERE p.cedula = :cedula", Paciente.class);
        query.setParameter("cedula", cedula);
        return query.getResultList();
    }

    public List<Paciente> buscarPorNombre(String nombre) {
        TypedQuery<Paciente> query = entityManager.createQuery("SELECT p FROM Paciente p WHERE LOWER(p.nombre) LIKE :nombre", Paciente.class);
        query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        return query.getResultList();
    }

    public Paciente buscarPorId(Long id) {
        return entityManager.find(Paciente.class, id);
    }
}
