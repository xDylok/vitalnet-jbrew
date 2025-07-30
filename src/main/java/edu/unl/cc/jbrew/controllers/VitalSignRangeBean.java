package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.VitalSignRangeRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import edu.unl.cc.jbrew.faces.FacesUtil;
import edu.unl.cc.jbrew.util.SignosVitalesUtil;
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

    public String getRowStyleClass(VitalSign sign) {
        if (range != null && SignosVitalesUtil.isPresionOutOfRange(sign.getPresionArterial(), range.getPresionNormal())) {
            return "warningRow";  // nombre de clase CSS para amarillo
        }
        return null;
    }

    public String save() {
        try {
            repository.save(range);
            FacesUtil.addSuccessMessageAndKeep("Aviso","Rango guardado correctamente");
            return "/indexLogin.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error","Error al guardar el rango: " + e.getMessage());
            return null;
        }
    }

    public boolean isPresionOutOfRange(String presionSign, String presionNormal) {
        return SignosVitalesUtil.isPresionOutOfRange(presionSign, presionNormal);
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
