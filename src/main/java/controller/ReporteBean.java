package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.model.Paciente;
import edu.unl.cc.jbrew.vitalnet.model.SignosVitales;
import edu.unl.cc.jbrew.vitalnet.service.PacienteService;
import edu.unl.cc.jbrew.vitalnet.service.SignosVitalesService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ReporteBean implements Serializable {

    private Long pacienteId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private List<Paciente> pacientes;
    private List<SignosVitales> resultados = new ArrayList<>();

    @Inject
    private SignosVitalesService signosService;

    @Inject
    private PacienteService pacienteService;

    @PostConstruct
    public void init() {
        pacientes = pacienteService.obtenerTodos();
    }

    public void generarReporte() {
        resultados = signosService.buscarPorPacienteYFecha(pacienteId, fechaInicio, fechaFin);
    }

    // Getters y Setters

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<SignosVitales> getResultados() {
        return resultados;
    }
}

