package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.service.ConfigService;
import edu.unl.cc.jbrew.vitalnet.model.Config;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ConfigBean implements Serializable {

    private Config config;

    @Inject
    private ConfigService configService;

    @PostConstruct
    public void init() {
        config = configService.obtenerConfiguracion();
    }

    public void guardar() {
        configService.actualizar(config);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Configuración actualizada correctamente"));
    }

    // Getters y Setters

    public Config getConfiguracion() {
        return config;
    }

    public void setConfiguracion(Config configuracion) {
        this.config = configuracion;
    }
}

