package edu.unl.cc.jbrew.vitalnet.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configuracion")
public class Config implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperaturaMax;
    private int frecuenciaCardiacaMin;
    private int frecuenciaCardiacaMax;
    private int presionArterialMax;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public int getFrecuenciaCardiacaMin() {
        return frecuenciaCardiacaMin;
    }

    public void setFrecuenciaCardiacaMin(int frecuenciaCardiacaMin) {
        this.frecuenciaCardiacaMin = frecuenciaCardiacaMin;
    }

    public int getFrecuenciaCardiacaMax() {
        return frecuenciaCardiacaMax;
    }

    public void setFrecuenciaCardiacaMax(int frecuenciaCardiacaMax) {
        this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
    }

    public int getPresionArterialMax() {
        return presionArterialMax;
    }

    public void setPresionArterialMax(int presionArterialMax) {
        this.presionArterialMax = presionArterialMax;
    }
}

