package com.facturito.model;

import java.util.List;

import com.facturito.entity.TFactura;
import com.facturito.entity.TFacturaDetalle;
import com.facturito.entity.TFormaPagoComprobantes;

public class FacturaDetalle {

	private TFactura factura;
	private List<TFacturaDetalle> detalle;
	private List<TFormaPagoComprobantes> formaPagoComprobante;

	public TFactura getFactura() {
		return factura;
	}

	public void setFactura(TFactura factura) {
		this.factura = factura;
	}

	public List<TFacturaDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<TFacturaDetalle> detalle) {
		this.detalle = detalle;
	}

	public List<TFormaPagoComprobantes> getFormaPagoComprobante() {
		return formaPagoComprobante;
	}

	public void setFormaPagoComprobante(List<TFormaPagoComprobantes> formaPagoComprobante) {
		this.formaPagoComprobante = formaPagoComprobante;
	}

}
