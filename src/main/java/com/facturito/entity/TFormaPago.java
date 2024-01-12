package com.facturito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_forma_pago")
public class TFormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forma_pago", nullable = false)
    private Long id;

    @Column(name = "detalle_forma_pago", length = 300)
    private String detalleFormaPago;

    @Column(name = "codigo_sri", length = 1000)
    private String codigoSri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalleFormaPago() {
        return detalleFormaPago;
    }

    public void setDetalleFormaPago(String detalleFormaPago) {
        this.detalleFormaPago = detalleFormaPago;
    }

    public String getCodigoSri() {
        return codigoSri;
    }

    public void setCodigoSri(String codigoSri) {
        this.codigoSri = codigoSri;
    }

}