package com.facturito.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.facturito.FacturaDTO;
import com.facturito.entity.TCliente;
import com.facturito.entity.TFactura;
import com.facturito.entity.TFacturaDetalle;
import com.facturito.model.FacturaDetalle;
import com.facturito.model.RespSimple;

public interface FacturaService {

//	public RespSimple guardarFactura(FacturaDetalle factura);
	
	
	public ResponseEntity<RespSimple> guardarFacturaWEB(FacturaDetalle factura);
	
	public ResponseEntity<RespSimple> actualizarDatosFacturaAutorizada(TFactura factura);
	
	public RespSimple actualizarEstadoFactura(TFactura factura);
	
	public RespSimple actualizarMensajeSRIFactura(TFactura factura);
	
	public RespSimple actualizarClaveAccesoSRIFactura(Long idCliente,String numComprobante, String claveAcceso);

	public TFactura getFactura(Long id);

	public TFacturaDetalle getDetalle(Long id);
	
	public List<TFacturaDetalle> getDetalleFactura(Long id);

	public List<FacturaDTO> obtenerUltimas3Facturas(Long idUsuario);

	public List<FacturaDTO> obtenerFacturasAutorizadasPorFecha(Long idCliente, Date fechaInicio, Date fechaFinal);
	
	public List<TFactura> obtenerPorNumeroComprobante(String numComprobante, Long idCliente);
	
	public List<TFactura> obtenerFacturasNoAutorizadasPorFechaIdentificacion(int idCliente, String fechaInicio, String fechaFinal);
	
	public List<TFactura> obtenerFacturas(Long idUsuario);
	
	public TFactura getFacturaByNumComprobante(String numComrpbante, TCliente cliente);
	
	public List<TFactura> obtenerTodasFacturasNoAutorizadas();
	
	public List<TFactura> obtenerFacturasNoEnviadas();
	
	
	//=============RESPORTES DASHBOARD==============
	public ResponseEntity<RespSimple> getFacturasTotalByClienteAndYear(Long idCliente);
	
	public ResponseEntity<RespSimple> getFacturasCount(Long idCliente);
	
	public ResponseEntity<RespSimple> getFacturasByClienteAndAnioOrMonth(Long idClient, int anio, int mes);

}
