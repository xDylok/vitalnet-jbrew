package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.VitalSignRangeRepository;
import edu.unl.cc.jbrew.bussiness.services.VitalSignRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
@Named("listaSignosVitalesBean")
@ViewScoped
public class ListaSignosVitalesBean implements Serializable {

    @Inject
    private VitalSignRepository vitalSignRepository;

    @Inject
    private VitalSignRangeRepository rangeRepository;

    private List<VitalSign> signs;
    private List<VitalSign> filteredSigns;

    private VitalSignRange range;

    private String criteria;

    @PostConstruct
    public void init() {
        signs = vitalSignRepository.findAll();
        range = rangeRepository.getConfig();
        if (range == null) {
            range = new VitalSignRange(); // valores vacíos para evitar nulls
        }
    }

    public void search() {
        if (criteria == null || criteria.trim().isEmpty()) {
            signs = vitalSignRepository.findAll();
        } else {
            signs = vitalSignRepository.findByCriteria(criteria);
        }
    }

    public void reset() {
        criteria = null;
        signs = vitalSignRepository.findAll();
    }

    // Este método será usado por la tabla para colorear la fila
    public String getRowStyleClass(VitalSign sign) {
        if (isPresionOutOfRange(sign.getPresionArterial(), range.getPresionNormal())) {
            return "warning-row";
        }
        return "";
    }

    // Lógica de comparación entre presión registrada y presión normal
    private boolean isPresionOutOfRange(String presionSign, String presionNormal) {
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

        } catch (Exception e) {
            return false;
        }
    }

    // Getters y setters omitidos por brevedad...


    public VitalSignRepository getVitalSignRepository() {
        return vitalSignRepository;
    }

    public void setVitalSignRepository(VitalSignRepository vitalSignRepository) {
        this.vitalSignRepository = vitalSignRepository;
    }

    public VitalSignRangeRepository getRangeRepository() {
        return rangeRepository;
    }

    public void setRangeRepository(VitalSignRangeRepository rangeRepository) {
        this.rangeRepository = rangeRepository;
    }

    public List<VitalSign> getSigns() {
        return signs;
    }

    public void setSigns(List<VitalSign> signs) {
        this.signs = signs;
    }

    public List<VitalSign> getFilteredSigns() {
        return filteredSigns;
    }

    public void setFilteredSigns(List<VitalSign> filteredSigns) {
        this.filteredSigns = filteredSigns;
    }

    public VitalSignRange getRange() {
        return range;
    }

    public void setRange(VitalSignRange range) {
        this.range = range;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
