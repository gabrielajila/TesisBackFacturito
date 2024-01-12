package com.facturito.model;

import java.math.BigDecimal;

public class Pago {
	public String formaPago;
    public BigDecimal total;
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
   
}
