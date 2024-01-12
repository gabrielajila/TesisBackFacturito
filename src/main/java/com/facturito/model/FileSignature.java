package com.facturito.model;

import com.facturito.entity.TCliente;

public class FileSignature {

	private String file64;
	private String logo64;
	private String path;
	private String password;
	private TCliente cliente;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public TCliente getCliente() {
		return cliente;
	}

	public void setCliente(TCliente cliente) {
		this.cliente = cliente;
	}

	public String getFile64() {
		return file64;
	}

	public void setFile64(String file64) {
		this.file64 = file64;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogo64() {
		return logo64;
	}

	public void setLogo64(String logo64) {
		this.logo64 = logo64;
	}
}
