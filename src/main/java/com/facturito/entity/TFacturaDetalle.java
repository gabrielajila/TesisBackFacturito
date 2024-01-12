package com.facturito.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_factura_detalle")
public class TFacturaDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_factura_detalle", nullable = false)
	private Long id;

	@Column(name = "cantidad")
	private BigDecimal cantidad;

	@Column(name = "tarifa")
	private BigDecimal tarifa;

	@Column(name = "descuento", precision = 12, scale = 2)
	private BigDecimal descuento;

	@Column(name = "valor_total", precision = 12, scale = 2)
	private BigDecimal valorTotal;

	@Column(name = "valor_ice", precision = 12, scale = 2)
	private BigDecimal valorIce;

	@ManyToOne
	@JoinColumn(name = "id_factura")
	private TFactura idFactura;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private TProducto idProducto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorIce() {
		return valorIce;
	}

	public void setValorIce(BigDecimal valorIce) {
		this.valorIce = valorIce;
	}

	public TFactura getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(TFactura idFactura) {
		this.idFactura = idFactura;
	}

	public TProducto getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(TProducto idProducto) {
		this.idProducto = idProducto;
	}

}