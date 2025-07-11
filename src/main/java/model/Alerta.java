package edu.unl.cc.jbrew.vitalnet.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertaMedica")
public class Alerta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descripcion;
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "signos_id")
    private SignosVitales signosVitales;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public SignosVitales getSignosVitales() {
        return signosVitales;
    }

    public void setSignosVitales(SignosVitales signosVitales) {
        this.signosVitales = signosVitales;
    }
}

