package edu.unl.cc.jbrew.vitalnet.service;

import edu.unl.cc.jbrew.vitalnet.model.Config;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class ConfigService {

    @PersistenceContext(unitName = "vitalnetPU")
    private EntityManager entityManager;

    public Config obtenerConfiguracion() {
        TypedQuery<Config> query = entityManager.createQuery("SELECT c FROM Config c", Config.class);
        return query.setMaxResults(1).getSingleResult();
    }

    public void actualizar(Config config) {
        entityManager.merge(config);
    }
}
