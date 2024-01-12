package com.facturito.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_codigos_ice")
public class TCodigosIce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codigo_ice", nullable = false)
    private Long id;

    @Column(name = "codigo_ice")
    private BigDecimal codigoIce;

    @Column(name = "descripcion_ice", length = 500)
    private String descripcionIce;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCodigoIce() {
        return codigoIce;
    }

    public void setCodigoIce(BigDecimal codigoIce) {
        this.codigoIce = codigoIce;
    }

    public String getDescripcionIce() {
        return descripcionIce;
    }

    public void setDescripcionIce(String descripcionIce) {
        this.descripcionIce = descripcionIce;
    }

}