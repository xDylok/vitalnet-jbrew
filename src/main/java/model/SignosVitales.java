package edu.unl.cc.jbrew.vitalnet.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "signosVitales")
public class SignosVitales implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperatura;
    private int frecuenciaCardiaca;
    private int presionArterial;
    private double peso;
    private double altura;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "pacienteId")
    private Paciente paciente;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public int getPresionArterial() {
        return presionArterial;
    }
    public void setPresionArterial(int presionArterial) {
        this.presionArterial = presionArterial;
    }
}

