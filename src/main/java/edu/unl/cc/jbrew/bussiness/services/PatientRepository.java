package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Patient;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class PatientRepository implements Serializable {

    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    public Patient find(Long id) {
        return entityManager.find(Patient.class, id);
    }

    public List<Patient> findAll() {
        return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    public List<Patient> search(String criteria) {
        if (criteria == null || criteria.trim().isEmpty()) {
            return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
        }

        return entityManager.createQuery(
                        "SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE :criteria OR LOWER(p.cedula) LIKE :criteria",
                        Patient.class
                )
                .setParameter("criteria", "%" + criteria.toLowerCase().trim() + "%")
                .getResultList();
    }


    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            entityManager.persist(patient);
            return patient;
        } else {
            return entityManager.merge(patient);
        }
    }

    public void delete(Long id) {
        Patient patient = find(id);
        if (patient != null) {
            entityManager.remove(patient);
        }
    }
}
