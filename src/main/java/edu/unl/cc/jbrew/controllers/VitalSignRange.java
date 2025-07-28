package edu.unl.cc.jbrew.controllers;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vital_sign_range")
public class VitalSignRange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float tempMin;
    private Float tempMax;
    private Integer frecuenciaMin;
    private Integer frecuenciaMax;
    private String presionNormal; // Ej: "120/80"
    private Float pesoMin;
    private Float pesoMax;


    public VitalSignRange() {

    }
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Integer getFrecuenciaMin() {
        return frecuenciaMin;
    }

    public void setFrecuenciaMin(Integer frecuenciaMin) {
        this.frecuenciaMin = frecuenciaMin;
    }

    public Integer getFrecuenciaMax() {
        return frecuenciaMax;
    }

    public void setFrecuenciaMax(Integer frecuenciaMax) {
        this.frecuenciaMax = frecuenciaMax;
    }

    public String getPresionNormal() {
        return presionNormal;
    }

    public void setPresionNormal(String presionNormal) {
        this.presionNormal = presionNormal;
    }

    public Float getPesoMin() {
        return pesoMin;
    }

    public void setPesoMin(Float pesoMin) {
        this.pesoMin = pesoMin;
    }

    public Float getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Float pesoMax) {
        this.pesoMax = pesoMax;
    }
}
