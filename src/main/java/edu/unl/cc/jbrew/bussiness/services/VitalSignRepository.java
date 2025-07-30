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
    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

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

    public VitalSign save(VitalSign sign) {
        if (sign.getId() == null) {
            entityManager.persist(sign);
            return sign;
        } else {
            return entityManager.merge(sign);
        }
    }

}
