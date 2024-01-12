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
@Table(name = "t_forma_pago_comprobante")
public class TFormaPagoComprobantes {

	@Id
	@Column(name = "id_comprobante_pago")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "num_dias")
	private int numDias;

	@Column(name = "fecha_transaccion")
	private Date fechaTransaccion;

	@Column(name = "fecha_vencimiento")
	private Date fechaVencimiento;

	@Column(name = "banco")
	private String banco;

	@Column(name = "num_documento")
	private String numDocumento;

	@ManyToOne
	@JoinColumn(name = "id_forma_pago")
	private TFormaPago formaPago;

	@Column(name = "caja")
	private String caja;

	@Column(name = "tipo_pago")
	private String tipoPago;

	@ManyToOne
	@JoinColumn(name = "id_factura")
	private TFactura factura;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TFormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(TFormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getNumDias() {
		return numDias;
	}

	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	

	public TFactura getFactura() {
		return factura;
	}

	public void setFactura(TFactura factura) {
		this.factura = factura;
	}

	

}
