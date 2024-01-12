package com.facturito.model;

public class Factura {
	public InfoTributaria infoTributaria;
	public InfoFactura infoFactura;
	public Detalles detalles;
	public InfoAdicional infoAdicional;
	public String id;
	public String version;

	public InfoTributaria getInfoTributaria() {
		return infoTributaria;
	}

	public void setInfoTributaria(InfoTributaria infoTributaria) {
		this.infoTributaria = infoTributaria;
	}

	public InfoFactura getInfoFactura() {
		return infoFactura;
	}

	public void setInfoFactura(InfoFactura infoFactura) {
		this.infoFactura = infoFactura;
	}

	public Detalles getDetalles() {
		return detalles;
	}

	public void setDetalles(Detalles detalles) {
		this.detalles = detalles;
	}

	public InfoAdicional getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(InfoAdicional infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
