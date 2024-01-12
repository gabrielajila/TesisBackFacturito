package com.facturito.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_kardex_no_inventariable")
public class TKardexNoInventariable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_kardex")
	private Long idKardex;

	@Column(name = "num_comprobante")
	private String numComprobante;

	@Column(name = "adquiriente")
	private String adquiriente;

	@Column(name = "cantidad")
	private BigDecimal cantidad;

	@Column(name = "precio_venta")
	private BigDecimal precioVenta;

	@Column(name = "fecha_movimiento")
	private Date fechaMovimiento;

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private TProducto producto;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private TCliente cliente;

	public Long getIdKardex() {
		return idKardex;
	}

	public void setIdKardex(Long idKardex) {
		this.idKardex = idKardex;
	}

	public String getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(String numComprobante) {
		this.numComprobante = numComprobante;
	}

	public String getAdquiriente() {
		return adquiriente;
	}

	public void setAdquiriente(String adquiriente) {
		this.adquiriente = adquiriente;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public TProducto getProducto() {
		return producto;
	}

	public void setProducto(TProducto producto) {
		this.producto = producto;
	}

	public TCliente getCliente() {
		return cliente;
	}

	public void setCliente(TCliente cliente) {
		this.cliente = cliente;
	}

}
