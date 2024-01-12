package com.facturito.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_detalle_forma_pago")
public class TDetalleFormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_forma_pago", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_forma_pago")
    private TFormaPago idFormaPago;

    @Column(name = "valor", precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(name = "plazo")
    private BigDecimal plazo;

    @Column(name = "tiempo", length = 100)
    private String tiempo;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_factura")
    private TFactura idFactura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TFormaPago getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(TFormaPago idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public TFactura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(TFactura idFactura) {
        this.idFactura = idFactura;
    }

}