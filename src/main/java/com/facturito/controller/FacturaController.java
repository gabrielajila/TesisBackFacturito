package com.facturito.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.FacturaDTO;
import com.facturito.entity.TFactura;
import com.facturito.entity.TFacturaDetalle;
import com.facturito.model.RespSimple;
import com.facturito.service.FacturaService;
import com.facturito.service.TFormaPagoComprobantesService;
import com.facturito.service.impl.FacturacionServiceImlp;
import com.facturito.util.DateOperations;
import com.facturito.model.DatosFactura;

@RestController
@RequestMapping("factura")
public class FacturaController {
	Logger log = Logger.getLogger(FacturacionController.class.getName());

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private FacturacionServiceImlp facturacionService;

	@Autowired
	private TFormaPagoComprobantesService formaPagoComprobantesService;

	@PostMapping(value = "facturar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> facturarWEB(@RequestBody DatosFactura datosFacturas) {
		return facturacionService.facturar(datosFacturas);
	}

	@PostMapping(value = "reenviarFacturaPorCorreo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> reenviarFacturaPorCorreo(@RequestParam Long idFactura,
			@RequestParam String correo) {
		return facturacionService.reenviarCorreo(idFactura, correo, null);
	}

	@PostMapping(value = "movil/reenviarFacturaPorCorreoByNumAut", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> reenviarFacturaPorCorreoByNumAut(@RequestParam String numAutorizacion,
			@RequestParam String correo) {
		System.out.println("paso por el correo api");
		return facturacionService.reenviarCorreo(null, correo, numAutorizacion);
	}

	@PostMapping(value = "getFactura", produces = MediaType.APPLICATION_JSON_VALUE)
	public TFactura getFactura() {
		return facturaService.getFactura(1l);
	}

	@PostMapping(value = "/getDetalleFactura", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TFacturaDetalle> getDetalleFactura(@RequestParam Long idFactura) {
		return facturaService.getDetalleFactura(idFactura);
	}

	@PostMapping(value = "/actualizarAutorizacionFactura", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> actualizarAutorizacionFactura(@RequestBody TFactura factura) {
		return facturaService.actualizarDatosFacturaAutorizada(factura);
	}

	@PostMapping(value = "obtenerUltimas3Facturas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FacturaDTO> obtenerUltimas3Facturas(@RequestParam Long idCliente) {
		return facturaService.obtenerUltimas3Facturas(idCliente);
	}

	// AUTORIZADAS
	@PostMapping(value = "obtenerFacturasPorFecha", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FacturaDTO> obtenerFacturasPorFechaIdentificacion(@RequestParam Long idCliente,
			@RequestParam String fechaInicio, @RequestParam String fechaFinal) {
		System.out.println("fecha: " + fechaInicio);
		System.out.println("fecha: " + fechaFinal);
		DateOperations asignarHora = new DateOperations();
		Date fechaI = DateOperations.getDateFormat("yyyy-MM-dd", fechaInicio);
		Date fechaF = DateOperations.getDateFormat("yyyy-MM-dd", fechaFinal);
		fechaI = asignarHora.asiganarHora(fechaI, "inicio");
		fechaF = asignarHora.asiganarHora(fechaF, "fin");
		return facturaService.obtenerFacturasAutorizadasPorFecha(idCliente, fechaI, fechaF);
	}

	// NO AUTORIZADAS
	@PostMapping(value = "obtenerFacturasPorNumComprobante", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TFactura> obtenerFacturasPorNumComprobante(@RequestParam Long idCliente, String numComprobante) {
		return facturaService.obtenerPorNumeroComprobante(numComprobante, idCliente);
	}

//	@PostMapping(value = "/obtenerFacturasNoAutorizadasPorFecha", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<TFactura> obtenerFacturasNoAutorizadasPorFecha(@RequestParam String idCliente,
//			@RequestParam Date fechaInicio, @RequestParam Date fechaFinal) {
//		DateOperations asignarHora = new DateOperations();
//		fechaInicio = asignarHora.asiganarHora(fechaInicio, "inicio");
//		fechaFinal = asignarHora.asiganarHora(fechaFinal, "fin");
//		return facturaService.obtenerFacturasNoAutorizadasPorFechaIdentificacion(Integer.valueOf(idCliente),
//				fechaInicio, fechaFinal);
//	}

	@PostMapping(value = "obtenerFacturas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TFactura> obtenerFacturas(@RequestParam Long idUsuario) {
		return facturaService.obtenerFacturas(idUsuario);
	}

	@PostMapping(value = "actualizarEstadoAutorizadoFactura", produces = MediaType.APPLICATION_JSON_VALUE)
	public RespSimple actualizarEstadoAutorzacionFactura(@RequestBody TFactura factura) {
		return facturaService.actualizarEstadoFactura(factura);
	}

	// ==========REPORTES DASHBOARD============
	@PostMapping("/getFacturasCount")
	public ResponseEntity<RespSimple> filtrarCantidadFacturasEmitidas(@RequestParam("idCliente") Long idCliente) {
		return facturaService.getFacturasCount(idCliente);
	}

	@PostMapping("/getFacturasTotalByClienteAndYear")
	public ResponseEntity<RespSimple> getFacturasTotalByClienteAndYear(@RequestParam("idCliente") Long idCliente) {
		return facturaService.getFacturasTotalByClienteAndYear(idCliente);
	}

	@PostMapping("/getFacturasByClienteAndAnioAndMes")
	public ResponseEntity<RespSimple> getFacturasByClienteAndAnioAndMes(@RequestParam("idCliente") Long idCliente,
			@RequestParam("anio") int anio, @RequestParam("mes") int mes) {
		return facturaService.getFacturasByClienteAndAnioOrMonth(idCliente, anio, mes);
	}

	// ===========VALOR DE LA FACTURAS EN EFECTIVO==========
	@PostMapping("/getValorFacturasEfectivoByCliente")
	public ResponseEntity<RespSimple> getValorFacturasEfectivoByCliente(@RequestParam("idCliente") Long idCliente) {
		return formaPagoComprobantesService.getValueFacturasEfectivo(idCliente);
	}

	@PostMapping("/getValorFacturasCreditoByCliente")
	public ResponseEntity<RespSimple> getValorFacturasCreditoByCliente(@RequestParam("idCliente") Long idCliente,
			@RequestParam("param") String param) {
		return formaPagoComprobantesService.getValueFacturasCredito(idCliente, param);
	}
}
