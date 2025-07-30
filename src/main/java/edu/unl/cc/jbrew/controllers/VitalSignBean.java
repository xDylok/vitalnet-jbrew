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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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

    @PersistenceContext(unitName = "mydb")
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        this.vitalSign = new VitalSign();
        this.users = userRepository.findAll(); // O el método que uses para obtener usuarios con rol médico/enfermero
    }

    public void save(Patient patient) {
        if (patient != null) {
            vitalSign.setPatient(patient);

            User responsable = userRepository.findByName(nombreResponsable);
            if (responsable != null) {
                vitalSign.setResponsable(responsable);

                // Obtener configuración de rangos normales
                VitalSignRange config = vitalSignRepository.getConfig(); // O por paciente, si aplica

                if (config != null && config.getPresionNormal() != null) {
                    String presionIngresada = vitalSign.getPresionArterial();
                    String presionNormal = config.getPresionNormal();

                    if (isPresionOutOfRange(presionIngresada, presionNormal)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Presión fuera del rango normal."));
                    }
                }

                vitalSignRepository.save(vitalSign);
                this.vitalSign = new VitalSign(); // reiniciar
                this.nombreResponsable = "";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Responsable no encontrado."));
            }
        }
    }



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

        } catch (NumberFormatException e) {
            // Si no puede parsear, no marcar como fuera de rango para no falsear el resultado
            return false;
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
