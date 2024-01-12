package com.facturito.util;

import org.springframework.stereotype.Service;

import com.facturito.model.RespSimple;

@Service
public class IdentificationValidator {

	public boolean validarCedulaEcuatoriana(String identificacion) {
		System.out.println("ced:   " + identificacion);
		// Implementar el algoritmo de validación de cédula ecuatoriana aquí
		// Puedes utilizar expresiones regulares o cualquier otro método de validación

		// Validación básica de longitud y estructura
		if (identificacion == null || identificacion.length() != 10 || !identificacion.matches("\\d{10}")) {
			System.out.println("debe ser de 10 digitos");
			return false;
		}

		// Extraer el dígito verificador de la cédula
		int digitoVerificador = Integer.parseInt(identificacion.substring(9));

		// Calcular el dígito verificador esperado
		int suma = 0;
		int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		for (int i = 0; i < 9; i++) {
			int digito = Integer.parseInt(identificacion.substring(i, i + 1));
			int producto = digito * coeficientes[i];
			if (producto > 9) {
				producto -= 9;
			}
			suma += producto;
		}
		int digitoVerificadorEsperado = 10 - (suma % 10);
		if (digitoVerificadorEsperado == 10) {
			digitoVerificadorEsperado = 0;

		}
		// Comparar el dígito verificador calculado con el dígito verificador ingresado
		return digitoVerificador == digitoVerificadorEsperado;
	}

	public RespSimple validarRUC(String number) {
		RespSimple response = new RespSimple();
		System.out.print("Ingrese el número de RUC: " + number);
		int dto = number.length();
		int valor;
		int acu = 0;
		try {
			if (number.equals("")) {
				System.out.println("No has ingresado ningún dato, por favor ingresar los datos correspondientes.");
				response.setCodigo("1");
				response.setMensaje("No has ingresado ningún dato, por favor ingresar los datos correspondientes.");
				return response;
			} else {
				for (int i = 0; i < dto; i++) {
					valor = Integer.parseInt(number.substring(i, i + 1));
					if (valor == 0 || valor == 1 || valor == 2 || valor == 3 || valor == 4 || valor == 5 || valor == 6
							|| valor == 7 || valor == 8 || valor == 9) {
						acu = acu + 1;
					}
				}
				if (acu == dto) {
					while (!number.substring(10, 13).equals("001")) {
//                        System.out.println("Los tres últimos dígitos no tienen el código del RUC 001.");
						response.setCodigo("1");
						response.setMensaje("Los tres últimos dígitos no tienen el código del RUC 001.");
						return response;
					}
					while (Integer.parseInt(number.substring(0, 2)) > 24) {
//                        System.out.println("Los dos primeros dígitos no pueden ser mayores a 24.");
						response.setCodigo("1");
						response.setMensaje("Los dos primeros dígitos no pueden ser mayores a 24.");
						return response;
					}
//                    System.out.println("El RUC está escrito correctamente");
//                    System.out.println("Se procederá a analizar el respectivo RUC.");
					int porcion1 = Integer.parseInt(number.substring(2, 3));
					if (porcion1 < 6) {
//                        System.out.println("El tercer dígito es menor a 6, por lo tanto el usuario es una persona natural.");
						response.setCodigo("0");
						response.setMensaje("El RUC pertenece a una persona natural");
						return response;
					} else {
						if (porcion1 == 6) {
//                            System.out.println("El tercer dígito es igual a 6, por lo tanto el usuario es una entidad pública.");
							response.setCodigo("0");
							response.setMensaje("El RUC pertenece a una entidad pública.");
							return response;
						} else {
							if (porcion1 == 9) {
//                                System.out.println("El tercer dígito es igual a 9, por lo tanto el usuario es una sociedad privada.");
								response.setCodigo("0");
								response.setMensaje("El RUC pertenece a una sociedad privada.");
								return response;
							}
						}
					}
				} else {
					response.setCodigo("1");
					response.setMensaje("ERROR: Por favor no ingrese texto");
					return response;
				}
			}
		} catch (Exception e) {
			response.setCodigo("1");
			response.setMensaje("Error al validar el RUC");
			return response;
		}
		response.setCodigo("1");
		response.setMensaje("Error al validar el RUC");
		return response;
	}
}