package edu.unl.cc.jbrew.vitalnet.controller;

import edu.unl.cc.jbrew.vitalnet.model.Paciente;
import edu.unl.cc.jbrew.vitalnet.service.PacienteService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

public class PacienteBean implements Serializable {
    private Paciente paciente = new Paciente();
    private List<Paciente> pacientes;

    @Inject
    private PacienteService pacienteService;
    @PostConstruct
    public void init() {
        pacientes = pacienteService.obtenerTodos();
    }
    public void save() {
        pacienteService.Save(paciente);
        pacientes = pacienteService.obtenerTodos();
        paciente = new Paciente();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente registrado exitosamente"));
    }

    public void delete(Paciente p) {
        pacienteService.Delete(p.getId());
        pacientes = pacienteService.obtenerTodos();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente eliminado"));
    }

    public void buscarPorCedula() {
        pacientes = pacienteService.buscarPorCedula(paciente.getCedula());
    }

    public void buscarPorNombre() {
        pacientes = pacienteService.buscarPorNombre(paciente.getNombre());
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}
