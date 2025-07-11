package edu.unl.cc.jbrew.vitalnet.service;

import edu.unl.cc.jbrew.vitalnet.model.Config;
import edu.unl.cc.jbrew.vitalnet.model.SignosVitales;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AlertaService {
    @Inject
    private ConfigService configService;
    /**
     * Determina si un registro de signos vitales debe generar alerta medica.
     */
    public boolean esAlerta(SignosVitales s) {
        Config cfg = configService.obtenerConfiguracion();
        return s.getTemperatura() > cfg.getTemperaturaMax()
                || s.getFrecuenciaCardiaca() < cfg.getFrecuenciaCardiacaMin()
                || s.getFrecuenciaCardiaca() > cfg.getFrecuenciaCardiacaMax()
                || s.getPresionArterial() > cfg.getPresionArterialMax();
    }
    /**
     * Devuelve el estilo CSS para resaltar registros con alerta.
     */
    public String colorAlerta(SignosVitales s) {
        return esAlerta(s) ? "color: red; font-weight: bold;" : "";
    }
    /**
     * Filtra los registros que representan alertas médicas.
     */
    public List<SignosVitales> filtrarAlertas(List<SignosVitales> registros) {
        List<SignosVitales> alertas = new ArrayList<>();
        for (SignosVitales s : registros) {
            if (esAlerta(s)) {
                alertas.add(s);
            }
        }
        return alertas;
    }
}

