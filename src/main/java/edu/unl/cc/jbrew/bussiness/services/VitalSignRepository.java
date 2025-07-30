package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.controllers.VitalSignRange;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import edu.unl.cc.jbrew.domain.security.Patient;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class VitalSignRepository implements Serializable {
//Lecturas o registros de signos vitales.
    @PersistenceContext(unitName = "vitaldb")
    private EntityManager entityManager;

    public List<VitalSign> findAll() {
        return entityManager.createQuery("SELECT v FROM VitalSign v ORDER BY v.fechaRegistro DESC", VitalSign.class)
                .getResultList();
    }

    public List<VitalSign> findByCriteria(String criteria) {
        // Aquí haces búsqueda básica por presión arterial o fecha (como ejemplo)
        return entityManager.createQuery("SELECT v FROM VitalSign v WHERE v.presionArterial LIKE :crit OR FUNCTION('TO_CHAR', v.fechaRegistro, 'YYYY-MM-DD') LIKE :crit ORDER BY v.fechaRegistro DESC", VitalSign.class)
                .setParameter("crit", "%" + criteria + "%")
                .getResultList();
    }
    public void delete(VitalSign sign) {
        VitalSign managed = entityManager.find(VitalSign.class, sign.getId());
        if (managed != null) {
            entityManager.remove(managed);
        }
    }

    public VitalSignRange getConfig() {
        return entityManager.createQuery("SELECT r FROM VitalSignRange r", VitalSignRange.class)
                .setMaxResults(1)
                .getSingleResult();
    }
    public VitalSign find(Long id) {
        return entityManager.find(VitalSign.class, id);
    }

    public List<VitalSign> findByPatient(Patient patient) {
        return entityManager.createQuery("SELECT v FROM VitalSign v WHERE v.patient = :patient ORDER BY v.fechaRegistro DESC", VitalSign.class)
                .setParameter("patient", patient)
                .getResultList();
    }
    public List<VitalSign> findVitalSignsByPatientId(Long patientId) {
        return entityManager.createQuery("SELECT v FROM VitalSign v WHERE v.patient.id = :id ORDER BY v.fechaRegistro DESC", VitalSign.class)
                .setParameter("id", patientId)
                .getResultList();
    }

    public VitalSign save(VitalSign sign) {
        if (sign.getId() == null) {
            entityManager.persist(sign);
            return sign;
        } else {
            return entityManager.merge(sign);
        }
    }

}
