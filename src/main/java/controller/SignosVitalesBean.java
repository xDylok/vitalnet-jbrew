package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.model.Paciente;
import edu.unl.cc.jbrew.vitalnet.model.SignosVitales;
import edu.unl.cc.jbrew.vitalnet.service.PacienteService;
import edu.unl.cc.jbrew.vitalnet.service.SignosVitalesService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
public class SignosVitalesBean implements Serializable {

    private SignosVitales signos = new SignosVitales();
    private List<SignosVitales> registros;
    private List<Paciente> pacientes;

    @Inject
    private SignosVitalesService signosService;

    @Inject
    private PacienteService pacienteService;

    @PostConstruct
    public void init() {
        pacientes = pacienteService.obtenerTodos();
        registros = signosService.obtenerTodos();
    }

    public void save() {
        signos.setFechaHora(LocalDateTime.now());
        signosService.save(signos);
        registros = signosService.obtenerTodos();
        signos = new SignosVitales();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Signos vitales registrados correctamente"));
    }

    public void delete(SignosVitales s) {
        signosService.delete(s.getId());
        registros = signosService.obtenerTodos();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Registro eliminado"));
    }

    // Getters y Setters

    public SignosVitales getSignos() {
        return signos;
    }

    public void setSignos(SignosVitales signos) {
        this.signos = signos;
    }

    public List<SignosVitales> getRegistros() {
        return registros;
    }

    public void setRegistros(List<SignosVitales> registros) {
        this.registros = registros;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}

