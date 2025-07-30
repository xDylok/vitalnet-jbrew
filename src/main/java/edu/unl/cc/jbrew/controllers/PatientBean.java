package edu.unl.cc.jbrew.controllers;

import edu.unl.cc.jbrew.bussiness.services.PatientRepository;
import edu.unl.cc.jbrew.controllers.security.UserSession;
import edu.unl.cc.jbrew.domain.security.Patient;
import edu.unl.cc.jbrew.faces.FacesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PatientBean implements Serializable {
//Lógica de interfaz para manejar pacientes.
    @PersistenceContext(unitName = "vitaldb")
    private EntityManager entityManager;
    @Inject
    private VitalSignBean vitalSignBean;

    private List<Patient> patients;
    private List<Patient> filteredPatients;

    private Patient patient;
    private Long selectedPatientId;

    private boolean managed;

    private String searchCriteria;

    @PostConstruct
    public void init() {
        list();
    }

    @Transactional
    public String createWithVitalSigns() {
        try {
            entityManager.persist(patient);
            entityManager.flush();

            vitalSignBean.save(patient);
            list();

            FacesUtil.addSuccessMessageAndKeep("Aviso", "Paciente y signos vitales guardados");
            return "patientList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error al crear paciente con signos vitales: " + e.getMessage());
            return null;
        }
    }


    public void list() {
        patients = entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
        filteredPatients = new ArrayList<>(patients);
    }

    public void loadPatient() {
        if (selectedPatientId != null) {
            patient = entityManager.find(Patient.class, selectedPatientId);
            managed = true;
        } else {
            patient = new Patient();
            managed = false;
        }
    }

    @Transactional
    public String update() {
        try {
            entityManager.merge(patient);
            list();
            FacesUtil.addSuccessMessageAndKeep("Aviso","Paciente actualizado correctamente");
            return "patientList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar paciente: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public String delete(Long patientId) {
        try {
            Patient p = entityManager.find(Patient.class, patientId);
            if (p != null) {
                entityManager.remove(p);
                list();
                FacesUtil.addSuccessMessageAndKeep("Notificacion","Paciente eliminado");
            } else {
                FacesUtil.addErrorMessage("Error","Paciente no encontrado");
            }
            return "patientList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error","Error al eliminar paciente: " + e.getMessage());
            return null;
        }
    }

    public void search() {
        if (searchCriteria == null || searchCriteria.isEmpty()) {
            filteredPatients = new ArrayList<>(patients);
        } else {
            String lower = searchCriteria.toLowerCase();
            filteredPatients = patients.stream().filter(p ->
                    (p.getFirstName() != null && p.getFirstName().toLowerCase().contains(lower)) ||
                            (p.getLastName() != null && p.getLastName().toLowerCase().contains(lower)) ||
                            (p.getCedula() != null && p.getCedula().toLowerCase().contains(lower)) ||
                            (p.getBirthDate() != null && p.getBirthDate().toString().contains(lower))
            ).toList();
        }
    }

    // Getters y Setters

    public List<Patient> getFilteredPatients() {
        return filteredPatients;
    }

    public void setFilteredPatients(List<Patient> filteredPatients) {
        this.filteredPatients = filteredPatients;
    }

    public Patient getPatient() {
        if (patient == null) {
            patient = new Patient();
        }
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getSelectedPatientId() {
        return selectedPatientId;
    }

    public void setSelectedPatientId(Long selectedPatientId) {
        this.selectedPatientId = selectedPatientId;
    }

    public boolean isManaged() {
        return managed;
    }

    public void setManaged(boolean managed) {
        this.managed = managed;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
