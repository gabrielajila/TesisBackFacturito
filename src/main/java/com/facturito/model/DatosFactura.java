package com.facturito.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.facturito.entity.TFormaPagoComprobantes;


public class DatosFactura {

	private String ruc;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String direccionComprador;
	private String telefonoComprador;
	private String emailComprador;
	private BigDecimal descuentoComprador;
	private String codigoIdentificacion;
	private String observacionesComprador;
	private String numComprobante;
	private Long idBodega;
	private Long idPuntoVenta;
	private Date fechaEmision;

	private List<DatosFacturasDetalle> lstDetalle;
	private List<TFormaPagoComprobantes> formaPagoComprobante;

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocialComprador() {
		return razonSocialComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public String getIdentificacionComprador() {
		return identificacionComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public String getDireccionComprador() {
		return direccionComprador;
	}

	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
	}

	public String getTelefonoComprador() {
		return telefonoComprador;
	}

	public void setTelefonoComprador(String telefonoComprador) {
		this.telefonoComprador = telefonoComprador;
	}

	public String getEmailComprador() {
		return emailComprador;
	}

	public void setEmailComprador(String emailComprador) {
		this.emailComprador = emailComprador;
	}

	public BigDecimal getDescuentoComprador() {
		return descuentoComprador;
	}

	public void setDescuentoComprador(BigDecimal descuentoComprador) {
		this.descuentoComprador = descuentoComprador;
	}

	public String getCodigoIdentificacion() {
		return codigoIdentificacion;
	}

	public void setCodigoIdentificacion(String codigoIdentificacion) {
		this.codigoIdentificacion = codigoIdentificacion;
	}

	public String getObservacionesComprador() {
		return observacionesComprador;
	}

	public void setObservacionesComprador(String observacionesComprador) {
		this.observacionesComprador = observacionesComprador;
	}

	public String getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(String numComprobante) {
		this.numComprobante = numComprobante;
	}

	public List<DatosFacturasDetalle> getLstDetalle() {
		return lstDetalle;
	}

	public void setLstDetalle(List<DatosFacturasDetalle> lstDetalle) {
		this.lstDetalle = lstDetalle;
	}

	public Long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Long idBodega) {
		this.idBodega = idBodega;
	}

	public Long getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Long idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public List<TFormaPagoComprobantes> getFormaPagoComprobante() {
		return formaPagoComprobante;
	}

	public void setFormaPagoComprobante(List<TFormaPagoComprobantes> formaPagoComprobante) {
		this.formaPagoComprobante = formaPagoComprobante;
	}

}
