package com.facturito.util;

public enum FacturitoEnum {

	NAME_DIR_AUTORIZADOS("autorizados", "autorizados"), NAME_DIR_FIRMADOS("firmados", "firmados"),
	BASE_IMPONIBLE("100", "BASE IMPONIBLE"), DESCUENTO_DEFAULT("0", "DESCUENTO DEFAULT"), CANTIDAD_UNO("1", "CANTIDAD"),
	PRODUCTO_DESCRIPCION("PRODUCTO 1", "PRODUCTO 1"), CODIGO_PRINCIPAL("COD01", "CODIGO PRINCIPAL"),
	// OTROS_CON_UTILIZACION_DEL_SISTEMA_FINANCIERO("20", "OTROS CON UTILIZACION DEL
	// SISTEMA FINANCIERO"),
	PAGO_EFECTIVO("01", "PAGOENEFECTIVO"), TOTAL_DESCUENTO("0", "VALOR_DESCUENTO"), VALOR_IVA("12", "VALORIVA"),
	// CODIGO_PORCENTAJE_IVA("2","PORCENTAJE IVA"),
	CODIGO_IVA("2", "IVA"), TIPO_IDENTIFICACION_CEDULA("05", "TIPO CEDULA"),
	SI_OBLIGADO_CONTABILIDAD("SI", "SI OBLIGADO"),
	CONTRIBUTENTE_RIMPE("CONTRIBUYENTE RÉGIMEN RIMPE", "CONTRIBUYENTE RIMPE"), CODIGODOC("01", "codigo documento"),
	ESTABLECIMIENTO("001", "ESTABLECIMIENTO"), PUNTO_EMISION("100", "PUNTO DE EMISION"),
	TIPO_EMISION("1", "tipo de emision"), TRANSACCION_OK("0", "Transaccion exitosa"),
	TRANSACCION_ERROR("1", "Transaccion erronea"), TRANSACCION_ERROR_BUT_NOTCLEAN_FRONT("2", "Transaccion erronea"),
	TRANSACCION_EXISTS("2", "Dato duplicado"), MCURRENT("1", "mcurrent"), USERADMIN("admin", "admin");

	private String id;
	private String descripcion;

	private FacturitoEnum(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
