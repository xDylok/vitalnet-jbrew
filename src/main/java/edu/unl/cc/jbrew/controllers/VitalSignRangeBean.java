package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.VitalSignRangeRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("vitalSignRangeBean")
@ViewScoped
public class VitalSignRangeBean implements Serializable {
//Posiblemente un DTO o clase auxiliar para mostrar info de signos vitales.
    private VitalSignRange range;
    private List<VitalSign> signs = new ArrayList<>();

    private Long patientId;

    @Inject
    private VitalSignRangeRepository repository;

    @PostConstruct
    public void init() {
        range = repository.getConfig();
        if (range == null) {
            range = new VitalSignRange();
        }
    }

    public void loadVitalSigns() {
        if (patientId != null) {
            signs = repository.findVitalSignsByPatientId(patientId);
        } else {
            signs = new ArrayList<>();
        }
    }

    public void save() {
        repository.save(range);
    }

    public VitalSignRange getRange() {
        return range;
    }

    public void setRange(VitalSignRange range) {
        this.range = range;
    }

    public List<VitalSign> getSigns() {
        return signs;
    }

    public void setSigns(List<VitalSign> signs) {
        this.signs = signs;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }


}
