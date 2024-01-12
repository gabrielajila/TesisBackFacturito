package com.facturito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_tarifa_iva" )
public class TTarifaIva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarifa_iva")
	private Long idTarifaIva;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "porcentaje")
	private Integer porcentaje;
	@Column(name = "codigo_sri")
	private String codigoSri;
	

	
	public Long getIdTarifaIva() {
		return idTarifaIva;
	}
	public void setIdTarifaIva(Long idTarifaIva) {
		this.idTarifaIva = idTarifaIva;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getCodigoSri() {
		return codigoSri;
	}
	public void setCodigoSri(String codigoSri) {
		this.codigoSri = codigoSri;
	}
	
	
}
