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
@Table(name = "t_adquiriente")
public class TAdquiriente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_adquiriente", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_tipo_identificacion")
	private TTipoIdentificacion idTipoIdentificacion;

	@Column(name = "razon_social", nullable = false, length = 250)
	private String razonSocial;

	@Column(name = "identificacion", nullable = false, length = 15)
	private String identificacion;

	@Column(name = "direccion", length = 500)
	private String direccion;

	@Column(name = "telefono", length = 250)
	private String telefono;

	@Column(name = "email_adquiriente", length = 300)
	private String emailAdquiriente;

	@Column(name = "descuento")
	private int descuento;

	@ManyToOne
	@JoinColumn(name = "id_tipo_adquiriente")
	private TTipoAdquiriente idTipoAdquiriente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TTipoIdentificacion getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(TTipoIdentificacion idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmailAdquiriente() {
		return emailAdquiriente;
	}

	public void setEmailAdquiriente(String emailAdquiriente) {
		this.emailAdquiriente = emailAdquiriente;
	}

	public TTipoAdquiriente getIdTipoAdquiriente() {
		return idTipoAdquiriente;
	}

	public void setIdTipoAdquiriente(TTipoAdquiriente idTipoAdquiriente) {
		this.idTipoAdquiriente = idTipoAdquiriente;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "TAdquiriente [id=" + id + ", idTipoIdentificacion=" + idTipoIdentificacion + ", razonSocial="
				+ razonSocial + ", identificacion=" + identificacion + ", direccion=" + direccion + ", telefono="
				+ telefono + ", emailAdquiriente=" + emailAdquiriente + ", descuento=" + descuento
				+ ", idTipoAdquiriente=" + idTipoAdquiriente + "]";
	}

}