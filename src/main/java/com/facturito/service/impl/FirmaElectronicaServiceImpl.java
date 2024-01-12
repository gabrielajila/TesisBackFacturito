package com.facturito.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.facturito.exception.CustomException;
import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;
import com.facturito.service.FirmaElectronicaService;
import com.facturito.util.FacturitoEnum;

@Service
public class FirmaElectronicaServiceImpl implements FirmaElectronicaService {

	private Logger log = Logger.getLogger(FirmaElectronicaServiceImpl.class.getName());
	@Value("${path.pc}")
	private String path;

	@Override
	public RespSimple almacenarArchivo(FileSignature fileSignature) {
		RespSimple respuestaSimple = new RespSimple();
		try {
			File directorio = new File(path.concat(fileSignature.getCliente().getIdentification()));
			if (!directorio.exists()) {
				if (directorio.mkdirs()) {
					log.info("DIRECTORIO CREADO CORRECTAMENTE");
					createSubDirectory(fileSignature);
					boolean respuestaCreacion = crearArchivo(fileSignature);
					if (respuestaCreacion) {
						respuestaSimple.setError(FacturitoEnum.TRANSACCION_OK.getId());
					} else {
						respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
						throw new CustomException("Error al verificar la firma o la clave de la misma.");
					}
					return respuestaSimple;
				} else {
					log.info("NO SE CREO EL DIRECTORIO");
					respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
					throw new CustomException("Error al crear el DIRECTORIO.");
				}
			} else {
				boolean respuestaAux = crearArchivo(fileSignature);
				if (respuestaAux) {
					respuestaSimple.setError(FacturitoEnum.TRANSACCION_OK.getId());
					respuestaSimple.setParametroRespuesta(directorio.getAbsolutePath());
				} else {
					respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
					throw new CustomException("Error al verificar la firma o la clave de la misma.");
				}
				return respuestaSimple;
			}

		} catch (CustomException e) {
			log.log(Level.SEVERE, "ERROR AL ALMACENAR EL ARCHIVO", e);
			respuestaSimple.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuestaSimple.setMensaje(e.getMessage());
			return respuestaSimple;
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL ALMACENAR EL ARCHIVO", e);
			respuestaSimple.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuestaSimple.setMensaje("ERROR AL ALMACENAR EL ARCHIVO");
			return respuestaSimple;
		}

	}

	public void createSubDirectory(FileSignature fileSignature) {
		try {
			log.info("CREAR SUBDIRECTORIOS");
			File directorioAutorizado = new File(path.concat("").concat(fileSignature.getCliente().getIdentification())
					.concat("/").concat(FacturitoEnum.NAME_DIR_AUTORIZADOS.getId()));
			File directorioFirmados = new File(path.concat("").concat(fileSignature.getCliente().getIdentification())
					.concat("/").concat(FacturitoEnum.NAME_DIR_FIRMADOS.getId()));
			directorioAutorizado.mkdirs();
			directorioFirmados.mkdirs();
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL CREAR EL SUBDIRECTORY ", e);
			throw new CustomException("Error al crear el subDIRECTORIO");
		}
	}

	public boolean crearArchivo(FileSignature fileSignature) {
		try {
			byte[] fileBytes = Base64.getDecoder().decode(fileSignature.getFile64());
			Path file = Files.write(Path.of(path.concat("/").concat(fileSignature.getCliente().getIdentification())
					.concat("/").concat(fileSignature.getCliente().getIdentification().concat(".p12"))), fileBytes);
			boolean validacion = validarFirma(file.toString(), fileSignature);
			if (!validacion) {
				Files.delete(file);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL CREAR EL ARCHIVO DE LA FIRMA ", e);
			throw new CustomException("ERROR AL CREAR EL ARCHIVO");
		}
	}

	public boolean validarFirma(String file, FileSignature fileSignature) {
		boolean respuesta = false;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream fileInputStream = new FileInputStream(file);
			keyStore.load(fileInputStream, fileSignature.getPassword().toCharArray());
			fileInputStream.close();
			Certificate certificate = keyStore.getCertificate(keyStore.aliases().nextElement());
			if (certificate instanceof X509Certificate) {
				X509Certificate x509Certificate = (X509Certificate) certificate;
				x509Certificate.checkValidity();
				System.out.println("Certificdo valido");
				respuesta = true;
			} else {
				System.out.println("Certificado invalido");
				respuesta = false;
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL VALIDAR LA FIRMA ", e);
			respuesta = false;
			return respuesta;
		}
		return respuesta;
	}

}
