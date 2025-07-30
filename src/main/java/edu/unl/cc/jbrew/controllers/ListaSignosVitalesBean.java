package edu.unl.cc.jbrew.controllers;

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

@Named("ListaSignosVitalesBean")
    @ViewScoped
    public class ListaSignosVitalesBean implements Serializable {

        @Inject
        private VitalSignRepository vitalSignRepository; // o el repositorio/DAO que uses para acceder a BD

        private List<VitalSign> signs;  // lista cargada
        private List<VitalSign> filteredSigns;  // lista filtrada para la tabla

        private String criteria; // criterio de búsqueda, ej: presión arterial o fecha

        @PostConstruct
        public void init() {
            signs = vitalSignRepository.findAll(); // carga todos los signos (podrías cargar solo los últimos o por paciente)
        }

        public void search() {
            if(criteria == null || criteria.isEmpty()) {
                signs = vitalSignRepository.findAll();
            } else {
                // Implementa método en repositorio para buscar por criterio
                signs = vitalSignRepository.findByCriteria(criteria);
            }
        }

        public void reset() {
            criteria = null;
            signs = vitalSignRepository.findAll();
        }

    public String edit(VitalSign sign) {
        // Guarda la ID en Flash o parámetro para cargar la edición en la otra vista
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editVitalSignId", sign.getId());
        return "/indexLogin.xhtml?faces-redirect=true";
    }

    public void delete(VitalSign sign) {
        try {
            vitalSignRepository.delete(sign);
            signs.remove(sign);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Signo vital eliminado."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar."));
        }
    }

    // getters y setters

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

        public String getCriteria() {
            return criteria;
        }

        public void setCriteria(String criteria) {
            this.criteria = criteria;
        }
    }

