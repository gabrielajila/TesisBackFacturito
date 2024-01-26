package com.facturito.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.facturito.model.FacturaDetalle;
import com.facturito.model.RespSimple;
import com.facturito.service.FacturaService;
import com.facturito.util.FacturitoEnum;
import com.facturito.FacturaDTO;
import com.facturito.DAO.TAdquirenteDAO;
import com.facturito.DAO.TClienteDAO;
import com.facturito.DAO.TFacturaDAO;
import com.facturito.DAO.TFacturaDetalleDAO;
import com.facturito.DAO.TFormaPagoDAO;
import com.facturito.DAO.TKardexNoInventariableDAO;
import com.facturito.DAO.TProductoDAO;
import com.facturito.DAO.TPuntoVentaDAO;
import com.facturito.entity.TCliente;
import com.facturito.entity.TFactura;
import com.facturito.entity.TFacturaDetalle;
import com.facturito.exception.CustomException;
import com.facturito.exception.CustomExceptionNoCleanData;
import com.facturito.entity.TFormaPagoComprobantes;
import com.facturito.entity.TProducto;
import com.facturito.entity.TFormaPago;
import com.facturito.entity.TKardexNoInventariable;
@Service
public class FacturaServiceImpl implements FacturaService {
	private Logger log = Logger.getLogger(FacturaServiceImpl.class.getName());

	@Autowired
	TFacturaDAO tFacturaDAO;

	@Autowired
	TAdquirenteDAO tAdquirenteDAO;

	@Autowired
	TClienteDAO tClienteDAO;

	@Autowired
	TFacturaDetalleDAO tFacturaDetalleDAO;

	@Autowired
	TProductoDAO tProductoDAO;

	@Autowired
	private TKardexNoInventariableDAO kardexNoInventariableDAO;

	@Autowired
	private TFormaPagoDAO formaPagoDAO;

	@Autowired
	private TPuntoVentaDAO puntoVentaDAO;


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseEntity<RespSimple> guardarFacturaWEB(FacturaDetalle factura) {
		RespSimple respuesta = new RespSimple();
		try {

			TFactura t_factura = new TFactura();

			t_factura.setIsAutorizado(false);
			t_factura.setFechaEmision(new Date());
			t_factura.setIdAdquiriente(factura.getFactura().getIdAdquiriente());
			t_factura.setIdCliente(factura.getFactura().getIdCliente());
			t_factura
					.setIdFormaPago(formaPagoDAO.findByCodigoSri(factura.getFactura().getIdFormaPago().getCodigoSri()));// ƒ
			t_factura.setAuthorizacion(""); // NO EXISTE AUN
			t_factura.setRide(""); // NO EXISTE AUN
			t_factura.setNumComprobante(factura.getFactura().getNumComprobante()); // NO EXISTE AUN
			t_factura.setObservacionesComprador(factura.getFactura().getObservacionesComprador());

			List<TFacturaDetalle> detalleItems = factura.getDetalle();
			BigDecimal valorIce = new BigDecimal(0);

			t_factura.setImpuestos(factura.getFactura().getImpuestos());
			t_factura.setSubtotal(factura.getFactura().getSubtotal());
			t_factura.setTotal(factura.getFactura().getTotal());
			t_factura.setValorIce(valorIce);
			t_factura.setDescuento(factura.getFactura().getDescuento());
			t_factura.setIntentosAutorizacion(0);
			//t_factura.setBodega(factura.getFactura().getBodega());
			t_factura.setPuntoVenta(factura.getFactura().getPuntoVenta());
			// HASTA VALIADAR LAS BODEGAS EN LA PRINCIPAL
			//t_factura.setBodega(bodegaDAO.findByClienteAndCodigo(t_factura.getIdCliente(), "01"));
			t_factura.setPuntoVenta(
					puntoVentaDAO.buscarByIdClienteEstablecimieto(t_factura.getIdCliente().getIdCliente()));
//			t_factura.setFormaPagoComprobante(factura.getFormaPagoComprobante());

			t_factura.setEnviada(false);
			TFormaPago formaPago = formaPagoDAO.findByCodigoSri("01");
			if (formaPago == null) {
				throw new CustomExceptionNoCleanData("Error al obtener la forma de pago");
			}

			TFactura facturaF = tFacturaDAO.save(t_factura);

			if (factura.getFormaPagoComprobante() == null) {
				TFormaPagoComprobantes formaPagoCom = new TFormaPagoComprobantes();
				formaPagoCom.setBanco("Efectivo");
				formaPagoCom.setFechaTransaccion(new Date());
				formaPagoCom.setFechaVencimiento(new Date());
				formaPagoCom.setFormaPago(formaPago);
				formaPagoCom.setCaja("Efectivo");
				formaPagoCom.setNumDias(1);
				formaPagoCom.setNumDocumento(t_factura.getNumComprobante());
				formaPagoCom.setTipoPago("Efectivo");
				formaPagoCom.setValor(t_factura.getTotal());
				formaPagoCom.setFactura(facturaF);
//				formaPagoComprobanteDAO.save(formaPagoCom);//CREAR PARA GUARDAR
			} 

			BigDecimal valorCostosTotalFactura = BigDecimal.ZERO;


			BigDecimal impuestos = BigDecimal.ZERO;
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			BigDecimal descuento = BigDecimal.ZERO;

			for (int i = 0; i < detalleItems.size(); i++) {
				TFacturaDetalle t_factura_detalle = new TFacturaDetalle();
				BigDecimal subtDet = detalleItems.get(i).getCantidad().multiply(detalleItems.get(i).getValorTotal());
				BigDecimal descDet = subtDet.multiply(detalleItems.get(i).getDescuento().divide(new BigDecimal(100)))
						.setScale(4, RoundingMode.HALF_UP);
				subtDet = subtDet.subtract(descDet).setScale(4, RoundingMode.HALF_UP);
				BigDecimal impDet = subtDet.multiply(detalleItems.get(i).getTarifa().divide(new BigDecimal(100)))
						.setScale(4, RoundingMode.HALF_UP);

				impuestos = impuestos.add(impDet);
				subtotal = subtotal.add(subtDet);
				total = total.add(subtDet.add(impDet));
				descuento = descuento.add(descDet);

				t_factura_detalle.setCantidad(detalleItems.get(i).getCantidad());
				t_factura_detalle.setTarifa(detalleItems.get(i).getTarifa());
				t_factura_detalle.setDescuento(detalleItems.get(i).getDescuento());
				t_factura_detalle.setValorTotal(detalleItems.get(i).getValorTotal());
				t_factura_detalle.setValorIce(detalleItems.get(i).getValorIce());
				t_factura_detalle.setIdFactura(facturaF);
				t_factura_detalle.setIdProducto(
						tProductoDAO.findById(factura.getDetalle().get(i).getIdProducto().getId()).get());
				tFacturaDetalleDAO.save(t_factura_detalle);

				TProducto productoDB = t_factura_detalle.getIdProducto();

				BigDecimal cantidadSaliente = t_factura_detalle.getCantidad().setScale(2, RoundingMode.HALF_UP);
				// PRODUCTOS NO INVENTARIABLES

					TKardexNoInventariable kardexNoInventariable = new TKardexNoInventariable();
					kardexNoInventariable.setCantidad(cantidadSaliente);
					kardexNoInventariable.setFechaMovimiento(new Date());
					kardexNoInventariable
							.setPrecioVenta(t_factura_detalle.getValorTotal().setScale(4, RoundingMode.HALF_UP));
					kardexNoInventariable.setCliente(productoDB.getCliente());
					kardexNoInventariable.setAdquiriente(t_factura.getIdAdquiriente().getIdentificacion() + " - "
							+ t_factura.getIdAdquiriente().getRazonSocial());
					kardexNoInventariable.setNumComprobante(t_factura.getNumComprobante());
					kardexNoInventariable.setProducto(productoDB);
					kardexNoInventariableDAO.save(kardexNoInventariable);
				

				ZoneId zonaEcuador = ZoneId.of("America/Guayaquil");
				LocalDateTime fechaHoraEcuador = LocalDateTime.now(zonaEcuador);

			}


			respuesta.setData(facturaF);
			respuesta.setError(FacturitoEnum.TRANSACCION_OK.getId());
			respuesta.setMensaje("Factura almacenada");
			return new ResponseEntity<RespSimple>(respuesta, HttpStatus.CREATED);
		} catch (CustomExceptionNoCleanData e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new CustomExceptionNoCleanData(e.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL GUARDAR FACTURA ", e);
			respuesta.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuesta.setMensaje(
					"Error al guardar la factura, factura no almacenada en la base de datos ni enviada al SRI.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<RespSimple> actualizarDatosFacturaAutorizada(TFactura factura) {
		TFactura t_factura = new TFactura();
		RespSimple response = new RespSimple();
		try {
			TCliente t_cliente = new TCliente();
			t_cliente = tClienteDAO.getCliente4identificacion(factura.getIdCliente().getIdentification());
			t_factura = tFacturaDAO.obtenerFacturaPorNumComprobante(factura.getNumComprobante(),
					t_cliente.getIdCliente());
			t_factura.setAuthorizacion(factura.getAuthorizacion());
			t_factura.setIsAutorizado(factura.getIsAutorizado());
			tFacturaDAO.save(t_factura);
			response.setCodigo("0");
			response.setDescripcion("Guardar autorizacion de factura");
			response.setMensaje("Factura autorizada.");
			return new ResponseEntity<RespSimple>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			response.setCodigo("1");
			response.setDescripcion("Guardar autorizacion de factura");
			response.setMensaje("Factura Generada pero no Autorizada.");
			response.setError("Error al actualizar el numero de autorizacion");
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public TFactura getFactura(Long id) {
		return tFacturaDAO.findById(id).orElse(null);
	}

	@Override
	public TFacturaDetalle getDetalle(Long id) {
		return tFacturaDetalleDAO.findById(id).orElse(null);
	}

	@Override
	public List<FacturaDTO> obtenerUltimas3Facturas(Long idCliente) {
		Pageable pageable = PageRequest.of(0, 3);
		return tFacturaDAO.obtenerUltimas3Facturas(idCliente, pageable);
	}

	@Override
	public List<FacturaDTO> obtenerFacturasAutorizadasPorFecha(Long idCliente, Date fechaInicio, Date fechaFinal) {
		return tFacturaDAO.obtenerFacturasAutorizadasPorFecha(idCliente, fechaInicio, fechaFinal);
	}


	@Override
	public List<TFactura> obtenerFacturas(Long idUsuario) {
		return tFacturaDAO.obtenerFacturas(idUsuario);
	} 

	@Override
	public List<TFacturaDetalle> getDetalleFactura(Long idFactura) {
		return tFacturaDetalleDAO.buscarDetalleByIdFactura(idFactura);
	}

	@Override
	@Transactional
	public RespSimple actualizarEstadoFactura(TFactura factura) {
		RespSimple response = new RespSimple();
		try {
			TFactura fact = new TFactura();
			fact = tFacturaDAO.findById(factura.getId()).get();
			fact.setIsAutorizado(factura.getIsAutorizado());
			tFacturaDAO.save(fact);
			response.setCodigo("0");
			response.setDescripcion("Estado actualizado");
			response.setError("");
			response.setMensaje("Estado actualizado");
			return response;
		} catch (Exception e) {
			response.setCodigo("1");
			response.setError("Error");
			response.setDescripcion("Estado no actualizado");
			response.setMensaje("Error al actualizar el estado");
			return response;
		}
	}

	@Override
	public TFactura getFacturaByNumComprobante(String numComrpbante, TCliente cliente) {
		return tFacturaDAO.findByNumComprobanteAndIdCliente(numComrpbante, cliente);
	}

	@Override
	public RespSimple actualizarMensajeSRIFactura(TFactura factura) {
		TFactura fact = tFacturaDAO.findById(factura.getId()).get();
		if (fact != null) {
			fact.setRespuestaSri(factura.getRespuestaSri());
			tFacturaDAO.save(fact);
		}
		return null;
	}

	@Override
	public List<TFactura> obtenerPorNumeroComprobante(String numComprobante, Long idCliente) {
		// TODO Auto-generated method stub
		TCliente cliente = new TCliente();
		cliente.setIdCliente(idCliente);
		return tFacturaDAO.findByIdClienteAndNumComprobante(cliente, numComprobante);
	}

	@Override
	public RespSimple actualizarClaveAccesoSRIFactura(Long idCliente, String numComprobante, String claveAcceso) {
		TCliente cliente = new TCliente();
		cliente.setIdCliente(idCliente);
		TFactura fact = tFacturaDAO.findByNumComprobanteAndIdCliente(numComprobante, cliente);
		if (fact != null) {
			fact.setAuthorizacion(claveAcceso);
			tFacturaDAO.save(fact);
		}
		return null;
	}

	@Override
	public List<TFactura> obtenerTodasFacturasNoAutorizadas() {
		return tFacturaDAO.findByIsAutorizado(false);
	}

	@Override
	public List<TFactura> obtenerFacturasNoEnviadas() {
		return tFacturaDAO.findByIsAutorizadoAndEnviada(true, false);
	}

	// ===========REPORYES DASHBOARD===========

	@Override
	public ResponseEntity<RespSimple> getFacturasCount(Long idCliente) {
		RespSimple response = new RespSimple();
		LocalDateTime fecha = LocalDateTime.now();
		try {
			Long numFacturas = tFacturaDAO.countByIdClienteAndAnio(idCliente, fecha.getYear());
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("num facturas obtenida");
			response.setData(numFacturas);
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Error al obtener la lista de facturas");
			response.setDescripcion(e.getMessage());
			response.setData(0);
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<RespSimple> getFacturasByClienteAndAnioOrMonth(Long idCliente, int anio, int mes) {

		RespSimple response = new RespSimple();
		try {
			if (mes == 0) {
//				response.setData(tFacturaDAO.buscarFacturasByClienteAndAnio(idCliente, anio));
			} else {
//				response.setData(tFacturaDAO.buscarFacturasByClienteAndAnioAndMes(idCliente, anio, mes));
			}
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("Lista obtenida");
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMensaje("Error al obtener la lista de facturas");
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setData(new ArrayList<>());
			response.setDescripcion(e.getMessage());
			return new ResponseEntity<RespSimple>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<RespSimple> getFacturasTotalByClienteAndYear(Long idCliente) {
		RespSimple response = new RespSimple();
		LocalDateTime fecha = LocalDateTime.now();
		try {
			BigDecimal sumFacturas = tFacturaDAO.sumByIdClienteAndAnio(idCliente, fecha.getYear());
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("suma de facturas obtenida");
			response.setData(sumFacturas);
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Error al obtener la lista de facturas");
			response.setDescripcion(e.getMessage());
			response.setData(BigDecimal.ZERO);
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<TFactura> obtenerFacturasNoAutorizadasPorFechaIdentificacion(int idCliente, String fechaInicio,
			String fechaFinal) {
		// TODO Auto-generated method stub
		return null;
	}


}
