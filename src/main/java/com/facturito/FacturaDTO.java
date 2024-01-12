package com.facturito;

import java.math.BigDecimal;
import java.util.Date;

public class FacturaDTO {
	private Long id;
	private String razonSocial;
	private Date fechaEmision;
	private BigDecimal total;
	private String authorizacion;
	private String numComprobante;

	public FacturaDTO(Long id, String razonSocial, Date fechaEmision, BigDecimal total, String authorizacion,
			String numComprobante) {
		super();
		this.id = id;
		this.razonSocial = razonSocial;
		this.fechaEmision = fechaEmision;
		this.total = total;
		this.authorizacion = authorizacion;
		this.numComprobante = numComprobante;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getAuthorizacion() {
		return authorizacion;
	}

	public void setAuthorizacion(String authorizacion) {
		this.authorizacion = authorizacion;
	}

	public String getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(String numComprobante) {
		this.numComprobante = numComprobante;
	}

}
