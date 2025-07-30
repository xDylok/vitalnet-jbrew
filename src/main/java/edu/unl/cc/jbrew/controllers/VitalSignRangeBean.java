package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.VitalSignRangeRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import edu.unl.cc.jbrew.faces.FacesUtil;
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
        try {
            String[] normalParts = presionNormal.split("/");
            String[] signParts = presionSign.split("/");

            if (normalParts.length != 2 || signParts.length != 2) {
                return false; // formato inválido, no marcar
            }

            int normalSistolica = Integer.parseInt(normalParts[0].trim());
            int normalDiastolica = Integer.parseInt(normalParts[1].trim());

            int signSistolica = Integer.parseInt(signParts[0].trim());
            int signDiastolica = Integer.parseInt(signParts[1].trim());

            int tolerancia = 10; // margen +/- 10

            boolean sistolicaFuera = signSistolica < (normalSistolica - tolerancia) || signSistolica > (normalSistolica + tolerancia);
            boolean diastolicaFuera = signDiastolica < (normalDiastolica - tolerancia) || signDiastolica > (normalDiastolica + tolerancia);

            return sistolicaFuera || diastolicaFuera;

        } catch (NumberFormatException e) {
            // Si no puede parsear, no marcar como fuera de rango para no falsear el resultado
            return false;
        }
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
