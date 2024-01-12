package com.facturito.exception;

public class CustomExceptionNoCleanData extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomExceptionNoCleanData(String mensaje) {
		super(mensaje);
	}
}
