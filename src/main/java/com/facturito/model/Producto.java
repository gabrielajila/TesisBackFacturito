package com.facturito.model;

import java.math.BigDecimal;

public class Producto {
	
    private BigDecimal idProducto;

    private String codigo;

    private String nombreProducto;

    private BigDecimal precioUnitario;

    private String informacionAdicional;

    private Integer idTarifaIva;

    private Integer idCodigoIce;
    
    private BigDecimal idProductoCliente;

	public BigDecimal getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(BigDecimal idProducto) {
		this.idProducto = idProducto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public Integer getIdTarifaIva() {
		return idTarifaIva;
	}

	public void setIdTarifaIva(Integer idTarifaIva) {
		this.idTarifaIva = idTarifaIva;
	}

	public Integer getIdCodigoIce() {
		return idCodigoIce;
	}

	public void setIdCodigoIce(Integer idCodigoIce) {
		this.idCodigoIce = idCodigoIce;
	}

	public BigDecimal getIdProductoCliente() {
		return idProductoCliente;
	}

	public void setIdProductoCliente(BigDecimal idProductoCliente) {
		this.idProductoCliente = idProductoCliente;
	}

	

}
