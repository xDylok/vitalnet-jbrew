package edu.unl.cc.jbrew.vitalnet.service;

import edu.unl.cc.jbrew.vitalnet.model.SignosVitales;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SignosVitalesService {

    @PersistenceContext(unitName = "vitalnetPU")
    private EntityManager entityManager;

    public void save(SignosVitales signo) {
        if (signo.getId() == null) {
            entityManager.persist(signo);
        } else {
            entityManager.merge(signo);
        }
    }

    public void delete(Long id) {
        SignosVitales vital = entityManager.find(SignosVitales.class, id);
        if (vital != null) {
            entityManager.remove(vital);
        }
    }

    public List<SignosVitales> obtenerTodos() {
        return entityManager.createQuery("SELECT s FROM SignosVitales s", SignosVitales.class).getResultList();
    }

    public List<SignosVitales> obtenerPorPaciente(Long pacienteId) {
        return entityManager.createQuery(
                        "SELECT s FROM SignosVitales s WHERE s.paciente.id = :id ORDER BY s.fechaHora DESC", SignosVitales.class)
                .setParameter("id", pacienteId)
                .getResultList();
    }
    public List<SignosVitales> buscarPorPacienteYFecha(Long pacienteId, LocalDate inicio, LocalDate fin) {
        return entityManager.createQuery(
                        "SELECT s FROM SignosVitales s WHERE s.paciente.id = :id AND s.fechaHora BETWEEN :inicio AND :fin ORDER BY s.fechaHora DESC",
                        SignosVitales.class)
                .setParameter("id", pacienteId)
                .setParameter("inicio", inicio.atStartOfDay())
                .setParameter("fin", fin.atTime(23, 59, 59))
                .getResultList();
    }

}
