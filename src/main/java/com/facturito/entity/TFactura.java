package com.facturito.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_factura")
public class TFactura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_factura", nullable = false)
	private Long id;

//	@Convert(disableConversion = true)
	@Column(name = "fecha_emision")
//	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaEmision;

	@Column(name = "observaciones_comprador")
	private String observacionesComprador;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "id_adquiriente")
	private TAdquiriente idAdquiriente;

	@Column(name = "impuestos", precision = 12, scale = 2)
	private BigDecimal impuestos;

	@Column(name = "subtotal", precision = 12, scale = 2)
	private BigDecimal subtotal;

	@Column(name = "total", precision = 12, scale = 2)
	private BigDecimal total;

	@Column(name = "valor_ice", precision = 12, scale = 2)
	private BigDecimal valorIce;

	@Column(name = "descuento", precision = 12, scale = 2)
	private BigDecimal descuento;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "id_cliente")
	private TCliente idCliente;

	@ManyToOne
	@JoinColumn(name = "id_forma_pago")
	private TFormaPago idFormaPago;

	@Column(name = "authorizacion", length = 300)
	private String authorizacion;

	@Column(name = "ride", length = 500)
	private String ride;

	@Column(name = "num_comprobante", length = 500)
	private String numComprobante;

	@Column(name = "is_autorizado")
	private Boolean isAutorizado;

	@Column(name = "respuesta_sri")
	private String respuestaSri;

	@Column(name = "intentos_autorizacion")
	private int intentosAutorizacion;

	@Column(name = "enviada")
	private Boolean enviada;

	@ManyToOne
	@JoinColumn(name = "id_punto_venta")
	private TPuntoVenta puntoVenta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public TAdquiriente getIdAdquiriente() {
		return idAdquiriente;
	}

	public void setIdAdquiriente(TAdquiriente idAdquiriente) {
		this.idAdquiriente = idAdquiriente;
	}

	public BigDecimal getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(BigDecimal impuestos) {
		this.impuestos = impuestos;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getValorIce() {
		return valorIce;
	}

	public void setValorIce(BigDecimal valorIce) {
		this.valorIce = valorIce;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public TCliente getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(TCliente idCliente) {
		this.idCliente = idCliente;
	}

	public TFormaPago getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(TFormaPago idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public String getAuthorizacion() {
		return authorizacion;
	}

	public void setAuthorizacion(String authorizacion) {
		this.authorizacion = authorizacion;
	}

	public String getRide() {
		return ride;
	}

	public void setRide(String ride) {
		this.ride = ride;
	}

	public String getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(String numComprobante) {
		this.numComprobante = numComprobante;
	}

	public Boolean getIsAutorizado() {
		return isAutorizado;
	}

	public void setIsAutorizado(Boolean is_autorizado) {
		this.isAutorizado = is_autorizado;
	}

	public String getRespuestaSri() {
		return respuestaSri;
	}

	public void setRespuestaSri(String respuestaSri) {
		this.respuestaSri = respuestaSri;
	}

	public String getObservacionesComprador() {
		return observacionesComprador;
	}

	public void setObservacionesComprador(String observacionesComprador) {
		this.observacionesComprador = observacionesComprador;
	}

	public int getIntentosAutorizacion() {
		return intentosAutorizacion;
	}

	public void setIntentosAutorizacion(int intentosAutorizacion) {
		this.intentosAutorizacion = intentosAutorizacion;
	}

	public Boolean getEnviada() {
		return enviada;
	}

	public void setEnviada(Boolean enviada) {
		this.enviada = enviada;
	}

	/*public TBodega getBodega() {
		return bodega;
	}

	public void setBodega(TBodega bodega) {
		this.bodega = bodega;
	}*/

	public TPuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(TPuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

}