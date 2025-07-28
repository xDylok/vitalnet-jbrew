package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.controllers.VitalSignRange;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
public class VitalSignRangeRepository implements Serializable {

    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    public VitalSignRange getConfig() {
        List<VitalSignRange> list = entityManager.createQuery("SELECT v FROM VitalSignRange v", VitalSignRange.class).getResultList();
        return list.isEmpty() ? null : list.get(0); // Solo uno global
    }

    public VitalSignRange save(VitalSignRange range) {
        if (range.getId() == null) {
            entityManager.persist(range);
            return range;
        } else {
            return entityManager.merge(range);
        }
    }

    public List<VitalSign> findVitalSignsByPatientId(Long patientId) {
        if (patientId == null) {
            return Collections.emptyList();
        }
        return entityManager.createQuery(
                        "SELECT v FROM VitalSign v WHERE v.patient.id = :patientId ORDER BY v.fechaRegistro DESC",
                        VitalSign.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}
