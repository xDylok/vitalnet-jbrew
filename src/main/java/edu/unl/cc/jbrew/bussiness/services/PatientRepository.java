package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Patient;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class PatientRepository implements Serializable {
//Acceso a datos para pacientes.
    @PersistenceContext(unitName = "vitaldb")
    private EntityManager entityManager;

    public Patient find(Long id) {
        return entityManager.find(Patient.class, id);
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
