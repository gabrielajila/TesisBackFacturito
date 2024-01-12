package com.facturito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_punto_venta")
public class TPuntoVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_punto_venta", nullable = false)
	private Long idPuntoVenta;

	@ManyToOne
	@JoinColumn(name = "id_establecimiento")
	private TEstablecimiento establecimiento;

	@Column(name = "punto_emision", length = 3)
	private String puntoEmision;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "activo")
	private Boolean activo;

	@Column(name = "principal")
	private Boolean principal;

	public TPuntoVenta() {
		super();
	}

	public TPuntoVenta(Long idPuntoVenta) {
		super();
		this.idPuntoVenta = idPuntoVenta;
	}

	public Long getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Long idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public TEstablecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(TEstablecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

}
