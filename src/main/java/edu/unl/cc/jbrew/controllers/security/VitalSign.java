package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.domain.security.Patient;
import edu.unl.cc.jbrew.domain.security.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "signosVitales")
public class VitalSign implements Serializable {
//Clase de gestión de signos vitales.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Float temperatura;
    private Integer frecuenciaCardiaca;
    private String presionArterial;
    private Float peso;
    private Float altura;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private User responsable; // médico, enfermero, etc.

    public VitalSign() {
        this.fechaRegistro = new Date();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getResponsable() {
        return responsable;
    }

    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }
}
