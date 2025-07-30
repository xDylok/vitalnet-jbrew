package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.VitalSignRangeRepository;
import edu.unl.cc.jbrew.bussiness.services.VitalSignRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import jakarta.annotation.PostConstruct;
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

    public String getRowStyleClass(VitalSign sign) {
        boolean fueraDeRango = false;

        if (presionFueraRango(sign.getPresionArterial(), range.getPresionNormal())) {
            fueraDeRango = true;
        }

        if (sign.getTemperatura() != null && (sign.getTemperatura() < range.getTempMin() || sign.getTemperatura() > range.getTempMax())) {
            fueraDeRango = true;
        }

        if (sign.getFrecuenciaCardiaca() != null && (sign.getFrecuenciaCardiaca() < range.getFrecuenciaMin() || sign.getFrecuenciaCardiaca() > range.getFrecuenciaMax())) {
            fueraDeRango = true;
        }

        if (esPesoInadecuado(sign.getPeso(), sign.getAltura())) {
            fueraDeRango = true;
        }

        return fueraDeRango ? "warning-row" : "";
    }

    private boolean esPesoInadecuado(Float peso, Float altura) {
        if (peso == null || altura == null || altura <= 0) return false;

        float imc = peso / (altura * altura);
        return imc < 18.5 || imc > 24.9;
    }

    private boolean presionFueraRango(String presionSign, String presionNormal) {
        try {
            int presionSignInt = Integer.parseInt(presionSign.trim());
            int presionNormalInt = Integer.parseInt(presionNormal.trim());

            int tolerancia = 10;

            return presionSignInt < (presionNormalInt - tolerancia) || presionSignInt > (presionNormalInt + tolerancia);

        } catch (Exception e) {
            return false;
        }
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

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
