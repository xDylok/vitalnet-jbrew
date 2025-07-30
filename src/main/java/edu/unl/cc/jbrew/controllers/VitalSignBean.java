package edu.unl.cc.jbrew.controllers;
import edu.unl.cc.jbrew.bussiness.services.UserRepository;
import edu.unl.cc.jbrew.bussiness.services.VitalSignRepository;
import edu.unl.cc.jbrew.controllers.security.VitalSign;
import edu.unl.cc.jbrew.domain.security.Patient;
import edu.unl.cc.jbrew.domain.security.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class VitalSignBean implements Serializable {

    private VitalSign vitalSign;
    private List<User> users;
    private String nombreResponsable;
    @Inject
    private VitalSignRepository vitalSignRepository;

    @Inject
    private UserRepository userRepository; // Asegúrate de tener este repositorio para listar responsables

    @PostConstruct
    public void init() {
        this.vitalSign = new VitalSign();
        this.users = userRepository.findAll(); // O el método que uses para obtener usuarios con rol médico/enfermero
    }

    public void save(Patient patient) {
        if (patient != null) {
            vitalSign.setPatient(patient);

            // Buscar responsable por nombre
            User responsable = userRepository.findByName(nombreResponsable);
            if (responsable != null) {
                vitalSign.setResponsable(responsable);
                vitalSignRepository.save(vitalSign);
                this.vitalSign = new VitalSign(); // Reiniciar
                this.nombreResponsable = "";        // Limpiar
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Responsable no encontrado."));
            }
        }
    }

    // Getters y Setters

    public VitalSign getVitalSign() {
        return vitalSign;
    }

    public void setVitalSign(VitalSign vitalSign) {
        this.vitalSign = vitalSign;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }
}
