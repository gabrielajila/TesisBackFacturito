package com.facturito.model;

import java.math.BigDecimal;

public class DatosFacturasDetalle {

	private Long id_producto;
	private String descripcion;
	private BigDecimal valor;
	private String codigo;
	private Long codigoIva;
	private BigDecimal valorIce;
	private BigDecimal cantidad;



	public Long getId_producto() {
		return id_producto;
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoIva() {
		return codigoIva;
	}

	public void setCodigoIva(Long codigoIva) {
		this.codigoIva = codigoIva;
	}

	public BigDecimal getValorIce() {
		return valorIce;
	}

	public void setValorIce(BigDecimal valorIce) {
		this.valorIce = valorIce;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

}
