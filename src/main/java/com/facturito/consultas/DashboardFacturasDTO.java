package com.facturito.consultas;

public class DashboardFacturasDTO {
	private int anio;
	private int mes;
	private int cantidad;

	public DashboardFacturasDTO(int anio, int mes, int cantidad) {
		super();
		this.anio = anio;
		this.mes = mes;
		this.cantidad = cantidad;
	}

	public DashboardFacturasDTO() {
		super();
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
