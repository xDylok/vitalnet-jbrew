package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.service.SignosVitalesService;
import edu.unl.cc.jbrew.vitalnet.model.SignosVitales;
import edu.unl.cc.jbrew.vitalnet.service.AlertaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AlertaBean implements Serializable {

    private List<SignosVitales> alertas;

    @Inject
    private AlertaService alertaService;

    @Inject
    private SignosVitalesService signosService;

    @PostConstruct
    public void init() {
        List<SignosVitales> todos = signosService.obtenerTodos();
        alertas = alertaService.filtrarAlertas(todos);
    }

    public List<SignosVitales> getAlertas() {
        return alertas;
    }

    public String getEstilo(SignosVitales s) {
        return alertaService.colorAlerta(s);
    }
}

