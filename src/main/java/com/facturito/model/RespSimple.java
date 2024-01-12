package com.facturito.model;

public class RespSimple {

	private String error;
	private String mensaje;
	private String parametroRespuesta;
	private String codigo;
	private String descripcion;
	private Object data;

	public RespSimple() {
		super();
	}

	public RespSimple(String codigo, String mensaje, Object data, String parametroRespuesta) {
		super();
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.parametroRespuesta = parametroRespuesta;
		this.data = data;
	}

	public RespSimple(String codigo, String mensaje, Object data) {
		super();
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.data = data;
	}

	public RespSimple(String error, String mensaje, String codigo, Object data) {
		super();
		this.error = error;
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.data = data;
		this.descripcion = "";
	}

	public RespSimple(String error, String mensaje, String codigo, String descripcion, Object data) {
		super();
		this.error = error;
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getParametroRespuesta() {
		return parametroRespuesta;
	}

	public void setParametroRespuesta(String parametroRespuesta) {
		this.parametroRespuesta = parametroRespuesta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{" + "\"error\": " + error + "," + "\"mensaje\": \"" + mensaje + "\"," + "\"parametroRespuesta\": "
				+ parametroRespuesta + "," + "\"codigo\": \"" + codigo + "\"," + "\"descripcion\": " + descripcion + ","
				+ "\"data\": " + data + "}";
	}

}
