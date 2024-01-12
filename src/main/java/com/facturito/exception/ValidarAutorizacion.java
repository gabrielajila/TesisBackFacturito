package com.facturito.exception;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidarAutorizacion {

	public String validarNumAutorizacion(String aut,
			Date fechaEmi, String rucProveedor, String establecimiento,
			String codTipoDocumento) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaEmision = dateFormat.format(fechaEmi).replace("/", "");

		String num = aut.substring(0, 48);

		String fecha = aut.substring(0, 8);
		String tipoCom = aut.substring(8, 10);
		String ruc = aut.substring(10, 23);
		String ambiente = aut.substring(23, 24);
//        String establecimiento = aut.substring(24, 39);
		String codigoNumerico = aut.substring(39, 47);
		String codigoNum = aut.substring(24, 47);
		String tipoEmi = aut.substring(47, 48);
//        String digitoVerificador = aut.substring(48, 49);
		// VALIDACIOND DE DATOS INGRESADOS
		String cadenaValidacion = fechaEmision + codTipoDocumento + rucProveedor + ambiente + establecimiento
				+ codigoNumerico + tipoEmi;
		System.out.println("num: " + num);
		System.out.println("val: " + cadenaValidacion);
		String claveAcceso = fecha + tipoCom + ruc + ambiente + codigoNum + tipoEmi;
		String claveValidada = agregaDigitoVerificador(claveAcceso);

//        System.out.println("es valido? =" + cadenaValidacion.equals(num));
		boolean datosIngresoValido = cadenaValidacion.equals(num);
		boolean numeroAutValido = aut.equals(claveValidada);

		String response = "0";
		if (!numeroAutValido) {
			response = "El número de autorización es inválido.";
			return response;
		}
		if (!datosIngresoValido) {
			response = "Revisar la información de los datos de la factura";
			return response;
		}
//        System.out.println(aut.equals(claveValidada));

		return response;
	}

	private String agregaDigitoVerificador(String code) {
		code = code.trim();
		Integer suma = Integer.valueOf(0);
		Integer sumt = Integer.valueOf(0);
		Integer con = Integer.valueOf(2);
		Integer j = code.length();
		for (int i = code.length(); i > 0; i--) {
			suma = Integer.parseInt(code.substring(i - 1, i)) * con;
			sumt = sumt + suma;
			j = j - 1;
			con = con == 7 ? 2 : con + 1;
		}
		sumt = 11 - sumt % 11;
		if (sumt == 11) {
			sumt = 0;
		}
		if (sumt == 10) {
			sumt = 1;
		}
		code = code + sumt;

		return code;
	}

}
