package com.facturito.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.facturito.DAO.TFacturaDAO;
import com.facturito.entity.TFactura;
import com.facturito.entity.TFacturaDetalle;
import com.facturito.model.DatosFactura;
import com.facturito.model.DatosFacturasDetalle;
import com.facturito.model.RespSimple;
import com.facturito.service.FacturaService;
import com.facturito.util.CrearFormatoCorreo;

@Component
public class ReenvioProgramadoFacturasService {
	Logger log = Logger.getLogger(ReenvioProgramadoFacturasService.class.getName());
	@Autowired
	FacturaService facturacionService;

	@Autowired
	TFacturaDAO facturaDAO;

	@Autowired
	ReenvioFacturacionService facturacionServiceWEB;

	@Autowired
	RestTemplate template;

	@Value("${property.path.enviacorreo}")
	private String urlEnviarCorreo;

	@Scheduled(fixedRate = 1200000) // Se ejecuta cada 2 horas
	public void doSomethingScheduled() {
		// Lógica de la tarea programada
		log.log(Level.INFO, "Tarea programada ejecutada");
		DatosFactura datosFactura = new DatosFactura();
		List<TFactura> listaFacturas = facturacionService.obtenerTodasFacturasNoAutorizadas();
		for (TFactura fac : listaFacturas) {
			if (fac.getIntentosAutorizacion() <= 2) {
				datosFactura = new DatosFactura();
				System.out.println(fac.getIsAutorizado());
				datosFactura.setFechaEmision(fac.getFechaEmision());
				datosFactura.setRuc(fac.getIdCliente().getIdentification());
				datosFactura.setRazonSocialComprador(fac.getIdAdquiriente().getRazonSocial());
				datosFactura.setIdentificacionComprador(fac.getIdAdquiriente().getIdentificacion());
				datosFactura.setDireccionComprador(fac.getIdAdquiriente().getDireccion());
				datosFactura.setTelefonoComprador(fac.getIdAdquiriente().getTelefono());
				datosFactura.setEmailComprador(fac.getIdAdquiriente().getEmailAdquiriente());
				datosFactura.setDescuentoComprador(new BigDecimal(0));// DESCUENTO ADQUIRIENTE CERO
				datosFactura.setCodigoIdentificacion(fac.getIdAdquiriente().getIdTipoIdentificacion().getCodigo());
				datosFactura.setObservacionesComprador(fac.getObservacionesComprador());
				datosFactura.setNumComprobante(fac.getNumComprobante());
				datosFactura.setLstDetalle(new ArrayList<>());
				List<TFacturaDetalle> facturaDetalle = facturacionService.getDetalleFactura(fac.getId());
				for (TFacturaDetalle fd : facturaDetalle) {
					DatosFacturasDetalle dtsDetalle = new DatosFacturasDetalle();
					dtsDetalle.setDescripcion(fd.getIdProducto().getNombreProducto());
					dtsDetalle.setCodigo(fd.getIdProducto().getCodigo());
					BigDecimal precioSinIva = new BigDecimal(0);
					BigDecimal iva = BigDecimal.valueOf(fd.getIdProducto().getIdTarifaIva().getPorcentaje())
							.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
					precioSinIva = fd.getIdProducto().getPrecioUnitario().divide(iva, 4, RoundingMode.HALF_UP);
					dtsDetalle.setValor(precioSinIva);
					dtsDetalle.setCantidad(fd.getCantidad());
					dtsDetalle.setCodigoIva(fd.getIdProducto().getIdTarifaIva().getIdTarifaIva());
					datosFactura.getLstDetalle().add(dtsDetalle);
				}

				ResponseEntity<RespSimple> response = facturacionServiceWEB.reenviarFactura(datosFactura);
				RespSimple resp = response.getBody();
				log.log(Level.WARNING, resp.getMensaje());
				TFactura fact = facturacionService.getFactura(fac.getId());
				fact.setIntentosAutorizacion(fact.getIntentosAutorizacion() + 1); 
				fact.setEnviada(false);
				facturaDAO.save(fact);
				try {
					enviarCorreo(fact);
				} catch (Exception e) {
					log.log(Level.SEVERE, "Error al enviar factura por email: " + e.getMessage());
				}
				log.log(Level.SEVERE, "response controlador automatico: " + resp.getMensaje());
			}
		}

	}

	@Scheduled(fixedRate = 1300000)
	public void enviarCorreos() {
		try {
			List<TFactura> facturasNoEnviadas = facturacionService.obtenerFacturasNoEnviadas();
			for (TFactura f : facturasNoEnviadas) {
//				String html = new CrearFormatoCorreo().generarCorreoHTML(f);
//				String correo = f.getIdAdquiriente().getEmailAdquiriente();
//				String claveAcceso = f.getAuthorizacion();
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//				parameters.add("claveAcceso", claveAcceso);
//				parameters.add("correoAdquiriente", correo);
//				parameters.add("cuerpo", html + "\n");
//				System.out.println("\n\n\n\n enviando \n\n\n\n\n");
//				HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
				try {
//					String respuesta = template.postForObject(urlEnviarCorreo, requestEntity, String.class);
//					System.out.println("respuesta correo: " + respuesta);
//					f.setEnviada(true);
//					facturaDAO.save(f);
					enviarCorreo(f);
				} catch (Exception e) {
					System.out.println("Error al enviar factura por email: " + e.getMessage());
				}

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}
	}

	private void enviarCorreo(TFactura f) {
		String html = new CrearFormatoCorreo().generarCorreoHTML(f);
		String correo = f.getIdAdquiriente().getEmailAdquiriente();
		String claveAcceso = f.getAuthorizacion();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("claveAcceso", claveAcceso);
		parameters.add("correoAdquiriente", correo);
		parameters.add("cuerpo", html + "\n");
		System.out.println("\n\n\n\n enviando: " + correo + " \n\n\n\n\n");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
		try {
			String respuesta = template.postForObject(urlEnviarCorreo, requestEntity, String.class);
			System.out.println("respuesta correo: " + respuesta);
			f.setEnviada(true);
			facturaDAO.save(f);
		} catch (Exception e) {
			System.out.println("Error al enviar factura por email: " + e.getMessage());
		}
	}

}
