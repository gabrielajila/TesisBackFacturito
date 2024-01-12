package com.facturito.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_establecimiento")
public class TEstablecimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_establecimiento", nullable = false)
	private Long idEstablecimiento;

	@Column(name = "establecimiento", length = 3)
	private String establecimiento;

	@Column(name = "nombre", length = 150)
	private String nombre;

	@Column(name = "direccion", length = 255)
	private String direccion;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	@JsonIgnoreProperties(value = { "clave", "fechaRegistro", "ciudad", "telefono", "emailCliente", "user",
			"archivoFirma", "claveFirma", "logo", "identification", "nombres", "apellidos" })
	TCliente cliente;

	@Column(name = "activo")
	private boolean activo;

	public TEstablecimiento(Long idEstablecimiento) {
		super();
		this.idEstablecimiento = idEstablecimiento;
	}

	public TEstablecimiento() {
		super();
	}

	public Long getIdEstablecimiento() {
		return idEstablecimiento;
	}

	public void setIdEstablecimiento(Long idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public TCliente getCliente() {
		return cliente;
	}

	public void setCliente(TCliente cliente) {
		this.cliente = cliente;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
