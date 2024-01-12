package com.facturito.controller;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.facturito.DAO.TAdquirenteDAO;
import com.facturito.DAO.TClienteDAO;
import com.facturito.DAO.TProductoDAO;
import com.facturito.DAO.TSecuenciaFacturaDAO;
import com.facturito.DAO.TTipoAdquirienteDAO;
import com.facturito.DAO.TTipoIdentificacionDAO;
import com.facturito.DAO.TarifaIvaDAO;
import com.facturito.entity.TAdquiriente;
import com.facturito.entity.TCliente;
import com.facturito.entity.TPeticion;
import com.facturito.entity.TSecuenciaFactura;
import com.facturito.entity.TTarifaIva;
import com.facturito.entity.TTipoAdquiriente;
import com.facturito.entity.TTipoIdentificacion;
import com.facturito.model.DatosFactura;
import com.facturito.model.DatosFacturasDetalle;
import com.facturito.model.InfoFactura;
import com.facturito.model.InfoTributaria;
import com.facturito.model.RespSimple;
import com.facturito.service.AdquirienteService;
import com.facturito.service.FacturaService;
import com.facturito.service.PeticionService;
import com.facturito.service.impl.FacturacionServiceImlp;
import com.facturito.util.FacturitoEnum;
import com.facturito.model.Factura;
import com.facturito.model.TotalConImpuestos;
import com.facturito.model.TotalImpuesto;
import com.facturito.model.Pagos;
import com.facturito.model.Pago;
import com.facturito.model.Detalle;
import com.facturito.model.Detalles;
import com.facturito.model.Impuesto;
import com.facturito.model.InfoAdicional;
import com.facturito.model.CampoAdicional;
import com.facturito.model.Impuestos;
import com.facturito.model.FacturaDetalle;
import com.facturito.entity.TFactura;
import com.facturito.entity.TFormaPago;
import com.facturito.entity.TProducto;
import com.facturito.entity.TFacturaDetalle;



@RestController
public class FacturacionController {

	@Autowired
	private TClienteDAO clienteDAO;

	@Autowired
	private TarifaIvaDAO tarifaIvaDAO;

//	@Autowired
//	private TFormaPagoDAO formaPagoDAO;

	@Autowired
	private TProductoDAO tProductoDAO;

	@Autowired
	private AdquirienteService adquirienteService;

	@Autowired
	private TSecuenciaFacturaDAO secuenciaFacturaDAO;

	@Autowired
	private TTipoAdquirienteDAO tTipoAdquirienteDAO;

	@Autowired
	private TAdquirenteDAO tAdquirenteDAO;

	@Autowired
	TTipoIdentificacionDAO tipoIdentificacionDAO;

	@Autowired
	PeticionService peticionService;

	@Autowired
	FacturaService facturaService;

	@Autowired
	RestTemplate template;

	@Autowired
	private FacturacionServiceImlp facturacionService;

	@Value("${property.ambiente.factura}")
	private String ambiente;

	@Value("${property.path.factura}")
	private String urlFacturar;
	Logger log = Logger.getLogger(FacturacionController.class.getName());

	@PostMapping(value = "facturarWEB", produces = MediaType.APPLICATION_JSON_VALUE)
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

		return facturacionService.reenviarCorreo(null, correo, numAutorizacion);
	}
	
	// SOLO PARA EL MOVIL
	@PostMapping(value = "facturar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> facturar(@RequestBody DatosFactura datosFacturas) {
		
		RespSimple respuesta = new RespSimple();
		Factura factura = new Factura();
		TAdquiriente adquirienteResp;
		try {
			if (adquirienteService.existeAdquiriente(datosFacturas.getIdentificacionComprador()) == true) {
				TAdquiriente t_Adquiriente = tAdquirenteDAO
						.findByIdentificacion(datosFacturas.getIdentificacionComprador());
				TTipoIdentificacion tipoIdentificacion = tipoIdentificacionDAO
						.findByCodigo(datosFacturas.getCodigoIdentificacion());
				t_Adquiriente.setIdentificacion(datosFacturas.getIdentificacionComprador());
				t_Adquiriente.setRazonSocial(datosFacturas.getRazonSocialComprador());
				t_Adquiriente.setDireccion(datosFacturas.getDireccionComprador());
				t_Adquiriente.setTelefono(datosFacturas.getTelefonoComprador());
				t_Adquiriente.setEmailAdquiriente(datosFacturas.getEmailComprador());
				t_Adquiriente.setIdTipoIdentificacion(tipoIdentificacion);
				t_Adquiriente.setDescuento(0);
				adquirienteResp = tAdquirenteDAO.save(t_Adquiriente);
			} else {
				TAdquiriente t_Adquiriente = new TAdquiriente();
				TTipoAdquiriente t_TipoAdquiriente = tTipoAdquirienteDAO.findById(1l).orElse(null);
				TTipoIdentificacion tipoIdentificacion = tipoIdentificacionDAO.findById(1l).get();
				t_Adquiriente.setIdTipoIdentificacion(tipoIdentificacion);
				t_Adquiriente.setIdentificacion(datosFacturas.getIdentificacionComprador());
				t_Adquiriente.setRazonSocial(datosFacturas.getRazonSocialComprador());
				t_Adquiriente.setDireccion(datosFacturas.getDireccionComprador());
				t_Adquiriente.setTelefono(datosFacturas.getTelefonoComprador());
				t_Adquiriente.setEmailAdquiriente(datosFacturas.getEmailComprador());
				t_Adquiriente.setDescuento(0);
				t_Adquiriente.setIdTipoAdquiriente(t_TipoAdquiriente);
				adquirienteResp = tAdquirenteDAO.save(t_Adquiriente);
			}

			TCliente cliente = clienteDAO.getCliente4identificacion(datosFacturas.getRuc());
			TSecuenciaFactura secuenciaFactura = secuenciaFacturaDAO.findByIdCliente(cliente);
			factura.setInfoTributaria(new InfoTributaria());
			factura.getInfoTributaria().setAmbiente(ambiente);
			factura.getInfoTributaria().setTipoEmision(FacturitoEnum.TIPO_EMISION.getId());
			factura.getInfoTributaria().setRazonSocial(cliente.getNombres().concat(" ").concat(cliente.getApellidos()));
			factura.getInfoTributaria().setRuc(cliente.getIdentification());
			factura.getInfoTributaria().setCodDoc(secuenciaFactura.getCodigoDocumento());
			factura.getInfoTributaria().setEstab(secuenciaFactura.getEstablecimiento());
			factura.getInfoTributaria().setPtoEmi(secuenciaFactura.getPuntoEmision());

			factura.getInfoTributaria().setDirMatriz(cliente.getCiudad());
			factura.getInfoTributaria().setContribuyenteRimpe(FacturitoEnum.CONTRIBUTENTE_RIMPE.getId());
			factura.setInfoFactura(new InfoFactura());

			// FECHA
			ZoneId zonaEcuador = ZoneId.of("America/Guayaquil");
			LocalDateTime fechaHoraEcuador = LocalDateTime.now(zonaEcuador);
			Date fecha = Date.from(fechaHoraEcuador.toInstant(ZoneOffset.UTC));
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			factura.getInfoFactura().setFechaEmision(dateFormat.format(fecha));

			factura.getInfoFactura().setDirEstablecimiento(cliente.getCiudad());
			factura.getInfoFactura().setObligadoContabilidad(FacturitoEnum.SI_OBLIGADO_CONTABILIDAD.getId());
			factura.getInfoFactura().setTipoIdentificacionComprador(datosFacturas.getCodigoIdentificacion());
			factura.getInfoFactura().setRazonSocialComprador(datosFacturas.getRazonSocialComprador());
			factura.getInfoFactura().setIdentificacionComprador(datosFacturas.getIdentificacionComprador());
			factura.getInfoFactura().setDireccionComprador(datosFacturas.getDireccionComprador());

			BigDecimal totalF = BigDecimal.ZERO;
			BigDecimal totalF2 = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			BigDecimal total2 = BigDecimal.ZERO;

			TotalImpuesto totalImpuesto12 = new TotalImpuesto();
			TotalImpuesto totalImpuesto0 = new TotalImpuesto();
			BigDecimal descuentoF = BigDecimal.ZERO;
			BigDecimal descuentoF2 = BigDecimal.ZERO;

			List<TotalImpuesto> lstTotalImpuesto = new ArrayList<>();
			for (DatosFacturasDetalle detalleT : datosFacturas.getLstDetalle()) {

				TTarifaIva tarifaIva = tarifaIvaDAO.findById(detalleT.getCodigoIva()).orElse(null);

				BigDecimal porcentual = BigDecimal.valueOf(tarifaIva.getPorcentaje()).divide(new BigDecimal("100"));

				if (datosFacturas.getLstDetalle().size() >= 1 && tarifaIva.getPorcentaje() == 12) {

					total = total.add(detalleT.getValor().multiply(detalleT.getCantidad()));
					descuentoF = total.multiply(datosFacturas.getDescuentoComprador()).divide(BigDecimal.valueOf(100));

					totalF = total.subtract(descuentoF);
					factura.getInfoFactura()
							.setTotalSinImpuestos(totalF.add(totalF2).setScale(2, RoundingMode.HALF_UP));
					factura.getInfoFactura()
							.setTotalDescuento(descuentoF.add(descuentoF2).setScale(2, RoundingMode.HALF_UP));
					totalImpuesto12.setCodigo(FacturitoEnum.CODIGO_IVA.getId());
					totalImpuesto12.setCodigoPorcentaje(tarifaIva.getCodigoSri());
					totalImpuesto12.setBaseImponible(totalF.setScale(2, RoundingMode.HALF_UP));
					totalImpuesto12.setTarifa(tarifaIva.getPorcentaje());
					totalImpuesto12.setValor(totalF.multiply(porcentual).setScale(2, RoundingMode.HALF_UP));
					if (!lstTotalImpuesto.contains(totalImpuesto12)) {
						lstTotalImpuesto.add(totalImpuesto12);
					}
				}
				if (datosFacturas.getLstDetalle().size() >= 1 && tarifaIva.getPorcentaje() == 0) {
					total2 = total2.add(detalleT.getValor().multiply(detalleT.getCantidad()));
					descuentoF2 = total2.multiply(datosFacturas.getDescuentoComprador())
							.divide(BigDecimal.valueOf(100));
					totalF2 = total2.subtract(descuentoF2);
					factura.getInfoFactura()
							.setTotalSinImpuestos(totalF2.add(totalF).setScale(2, RoundingMode.HALF_UP));
					factura.getInfoFactura()
							.setTotalDescuento(descuentoF2.add(descuentoF).setScale(2, RoundingMode.HALF_UP));
					totalImpuesto0.setCodigo(FacturitoEnum.CODIGO_IVA.getId());
					totalImpuesto0.setCodigoPorcentaje(tarifaIva.getCodigoSri());
					totalImpuesto0.setBaseImponible(totalF2.setScale(2, RoundingMode.HALF_UP));
					totalImpuesto0.setTarifa(tarifaIva.getPorcentaje());
					totalImpuesto0.setValor(totalF2.multiply(porcentual).setScale(2, RoundingMode.HALF_UP));
					if (!lstTotalImpuesto.contains(totalImpuesto0)) {
						lstTotalImpuesto.add(totalImpuesto0);
					}
				}

			}
			TotalConImpuestos totalConImpuesto = new TotalConImpuestos();
			totalConImpuesto.setTotalImpuesto(lstTotalImpuesto);
			factura.getInfoFactura().setTotalConImpuestos(totalConImpuesto);
			factura.getInfoFactura()
					.setImporteTotal(totalF.add(totalF2)
							.add(totalImpuesto12.getValor() != null ? totalImpuesto12.getValor() : new BigDecimal("0"))
							.add(totalImpuesto0.getValor() != null ? totalImpuesto0.getValor() : new BigDecimal("0"))
							.setScale(2, RoundingMode.HALF_UP));

			Pagos pagos = new Pagos();
			Pago pago = new Pago();
			pago.setFormaPago(FacturitoEnum.PAGO_EFECTIVO.getId());
			// pago.setFormaPago(formaPagoDAO.getFormaPago4Id(Integer.parseInt(ContfiablesEnum.PAGO_EFECTIVO.getId())).getCodigoSri());
			pago.setTotal(totalF.add(totalF2)
					.add(totalImpuesto12.getValor() != null ? totalImpuesto12.getValor() : new BigDecimal("0"))
					.add(totalImpuesto0.getValor() != null ? totalImpuesto0.getValor() : new BigDecimal("0"))
					.setScale(2, RoundingMode.HALF_UP));
			List<Pago> lstPago = new ArrayList<>();
			lstPago.add(pago);
			pagos.setPago(lstPago);
			factura.getInfoFactura().setPagos(pagos);
			BigDecimal totalImpuestos = new BigDecimal(0);
			Detalles detalles = new Detalles();
			List<Detalle> lstDetalle = new ArrayList<>();
			for (DatosFacturasDetalle detalleP : datosFacturas.getLstDetalle()) {
				Detalle detalle = new Detalle();
				detalle.setCodigoPrincipal(detalleP.getCodigo());
				detalle.setCodigoAuxiliar(detalleP.getCodigo());
				detalle.setDescripcion(detalleP.getDescripcion());
				detalle.setCantidad(detalleP.getCantidad());
				detalle.setPrecioUnitario(detalleP.getValor().setScale(4, RoundingMode.HALF_UP));
				detalle.setDescuento(detalleP.getValor()
						.multiply(datosFacturas.getDescuentoComprador().divide(new BigDecimal("100")))
						.setScale(2, RoundingMode.HALF_UP));
				BigDecimal totalSinIva = detalleP.getValor().multiply(detalleP.getCantidad());
				detalle.setPrecioTotalSinImpuesto(totalSinIva
						.subtract(totalSinIva
								.multiply(datosFacturas.getDescuentoComprador().divide(new BigDecimal("100"))))
						.setScale(2, RoundingMode.HALF_UP));
				Impuestos impuestos = new Impuestos();
				Impuesto impuesto = new Impuesto();
				TTarifaIva tarifaIvaDetalle = tarifaIvaDAO.findById(detalleP.getCodigoIva()).orElse(null);
				BigDecimal porcentualDetalle = BigDecimal.valueOf(tarifaIvaDetalle.getPorcentaje())
						.divide(new BigDecimal("100"));
				impuesto.setCodigo(FacturitoEnum.CODIGO_IVA.getId());
				impuesto.setCodigoPorcentaje(tarifaIvaDetalle.getCodigoSri());
				impuesto.setTarifa(tarifaIvaDetalle.getPorcentaje());
				impuesto.setBaseImponible(totalSinIva
						.subtract(totalSinIva
								.multiply(datosFacturas.getDescuentoComprador().divide(new BigDecimal("100"))))
						.setScale(2, RoundingMode.HALF_UP));
				impuesto.setValor(totalSinIva
						.subtract(totalSinIva
								.multiply(datosFacturas.getDescuentoComprador().divide(new BigDecimal("100"))))
						.multiply(porcentualDetalle).setScale(2, RoundingMode.HALF_UP));
				totalImpuestos = totalImpuestos.add(impuesto.getValor());
				List<Impuesto> lstImpuesto = new ArrayList<>();
				lstImpuesto.add(impuesto);
				System.out.println(impuesto);
				impuestos.setImpuesto(lstImpuesto);
				detalle.setImpuestos(impuestos);
				lstDetalle.add(detalle);
			}

			detalles.setDetalle(lstDetalle);
			factura.setDetalles(detalles);

			InfoAdicional infoAdicional = new InfoAdicional();
			CampoAdicional campoAdicional = new CampoAdicional();
			CampoAdicional campoAdicional2 = new CampoAdicional();
			CampoAdicional campoAdicional3 = new CampoAdicional();
			campoAdicional.setValue(datosFacturas.getEmailComprador());
			campoAdicional.setNombre("Correo:");
			campoAdicional2.setValue(datosFacturas.getTelefonoComprador());
			campoAdicional2.setNombre("Celular/Telefono:");
			campoAdicional3.setValue(datosFacturas.getObservacionesComprador().isEmpty() ? "Ninguna"
					: datosFacturas.getObservacionesComprador());
			campoAdicional3.setNombre("Observaciones:");

			List<CampoAdicional> lstCampoAdicional = new ArrayList<>();
			lstCampoAdicional.add(campoAdicional);
			lstCampoAdicional.add(campoAdicional2);
			lstCampoAdicional.add(campoAdicional3);
			infoAdicional.setCampoAdicional(lstCampoAdicional);
			factura.setInfoAdicional(infoAdicional);

			// CAMBIO COMO ULKTIMO PASO PARA EVITAR ERRORES DEL SALTO DE NUM COMPROBANTE
			factura.getInfoTributaria()
					.setSecuencial(datosFacturas.getNumComprobante().isEmpty() ? getSecuencia(cliente)
							: datosFacturas.getNumComprobante());
			// GUARDAR FACTURA AQUI
			TFactura tFacturaGuardad = new TFactura();
			if (datosFacturas.getNumComprobante().isEmpty()) {
//				guardarFacturaWeb
				FacturaDetalle facturaDetalle = new FacturaDetalle();
				TFactura tFact = new TFactura();
				TFormaPago tFormaPago = new TFormaPago();

				System.out.println("subtotal: " + factura.getInfoFactura().getTotalSinImpuestos());
				System.out.println("impuestos" + totalImpuestos);
				System.out.println("impuesto calculado"
						+ factura.getInfoFactura().getTotalSinImpuestos().multiply(new BigDecimal(0.12)));
				System.out.println("");
				System.out.println("");

				tFact.setIdAdquiriente(adquirienteResp);
				tFact.setIdCliente(cliente);
				tFact.setNumComprobante(factura.getInfoTributaria().getSecuencial());
				tFact.setSubtotal(factura.getInfoFactura().getTotalSinImpuestos());
				tFact.setTotal(factura.getInfoFactura().getImporteTotal());
				tFact.setDescuento(factura.getInfoFactura().getTotalDescuento());
				tFact.setImpuestos(totalImpuestos);
				tFact.setObservacionesComprador(campoAdicional3.getValue());

				tFormaPago.setCodigoSri(pago.getFormaPago());
				tFact.setIdFormaPago(tFormaPago);// DEPENDE DE LA FORMA DE PAGO DE LA FACTURA

				facturaDetalle.setFactura(tFact);

				List<TFacturaDetalle> lstListaProductos = new ArrayList<>();
				for (DatosFacturasDetalle detalleF : datosFacturas.getLstDetalle()) {
					TFacturaDetalle detalleBD = new TFacturaDetalle();
					TProducto producto = tProductoDAO.findById(detalleF.getId_producto()).get();
					TTarifaIva tarifaIva = tarifaIvaDAO.findById(detalleF.getCodigoIva()).orElse(null);
					detalleBD.setIdProducto(producto);
					detalleBD.setCantidad(detalleF.getCantidad());
					detalleBD.setTarifa(BigDecimal.valueOf(tarifaIva.getPorcentaje()));
					detalleBD.setValorIce(detalleF.getValorIce());
					detalleBD.setDescuento(datosFacturas.getDescuentoComprador());
					detalleBD.setValorTotal(detalleF.getValor());
					lstListaProductos.add(detalleBD);
				}
				facturaDetalle.setDetalle(lstListaProductos);

				ResponseEntity<RespSimple> response = facturaService.guardarFacturaWEB(facturaDetalle);
				if (response.getBody().getError() != "0") {
					return response;
				}

			}
			tFacturaGuardad = facturaService.getFacturaByNumComprobante(factura.getInfoTributaria().getSecuencial(),
					cliente);
			if (tFacturaGuardad != null) {
				if (tFacturaGuardad.getFechaEmision() != null) {
					factura.getInfoFactura().setFechaEmision(dateFormat.format(tFacturaGuardad.getFechaEmision()));
					System.out.println("fecha emision factura: " + factura.getInfoFactura().getFechaEmision());
				}
			}
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			respuesta.setError("");
			respuesta.setMensaje("Factura registrada");

//			Gson gson = new Gson();
//			String json = gson.toJson(factura);
//			System.out.println(json);
			try {
				respuesta = template.postForObject(urlFacturar, factura, RespSimple.class);
				if (respuesta.getDescripcion() == null) {
					respuesta.setError("Alerta");
					respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
					respuesta.setMensaje("Factura creada pero no autorizada");
					return new ResponseEntity<RespSimple>(respuesta, HttpStatus.OK);
				}
				respuesta.setError("");
				respuesta.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
				respuesta.setMensaje(FacturitoEnum.TRANSACCION_OK.getDescripcion());
				tFacturaGuardad.setRespuestaSri(respuesta.getDescripcion());
				facturaService.actualizarMensajeSRIFactura(tFacturaGuardad);
				// gurdar el la clave obtenida

				TPeticion peticion = peticionService.obtenerPeticionPorNumeroComprobanteYidCliente(
						factura.getInfoTributaria().getSecuencial(), Long.valueOf(cliente.getIdCliente().toString()));
				if (peticion != null) {
					String claveAcces = peticion.getClaveAcceso();
					String autorizacion = peticion.getNumeroAutorizacion();
					facturaService.actualizarClaveAccesoSRIFactura(cliente.getIdCliente(),
							factura.getInfoTributaria().getSecuencial(), claveAcces);
					if (claveAcces != null && autorizacion.equals(claveAcces)) {
						tFacturaGuardad.setIsAutorizado(true);
						facturaService.actualizarEstadoFactura(tFacturaGuardad);
					}
				}
			} catch (Exception e) {
				respuesta.setError("Alerta");
				respuesta.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
				respuesta.setMensaje("Factura creada pero no autorizada");
				System.out.println("ERROR: " + e.getMessage());
				return new ResponseEntity<RespSimple>(respuesta, HttpStatus.OK);
			}
			return new ResponseEntity<RespSimple>(respuesta, HttpStatus.CREATED);
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL REALIZAR LA FACTURACION ", e);
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuesta.setError("Error");
			respuesta.setMensaje("Error en el proceso de facturación");
			respuesta.setDescripcion(e.getMessage());

			return new ResponseEntity<RespSimple>(respuesta, HttpStatus.OK);
		}

	}

	public String getSecuencia(TCliente Cliente) {
		String respuesta = "";
		try {
			TSecuenciaFactura secuenciaRespuesta = secuenciaFacturaDAO.findByIdCliente(Cliente);
			if (secuenciaRespuesta.getSecuencial().toString().length() < 9) {
				respuesta = secuenciaRespuesta.getSecuencial().toString();
				for (int i = respuesta.length(); i < 9; i++) {
					respuesta = "0".concat(respuesta);
				}
				secuenciaRespuesta.setSecuencial(secuenciaRespuesta.getSecuencial() + 1);
				secuenciaRespuesta = secuenciaFacturaDAO.save(secuenciaRespuesta);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "error al obtener las secuencias ", e);
		}
		return respuesta;
	}
	

}