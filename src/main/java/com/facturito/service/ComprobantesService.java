package com.facturito.service;

import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.facturito.model.RespSimple;


public interface ComprobantesService {

	
	

	public ResponseEntity<RespSimple> eliminarComprobanteCompra(Long idComprobanteCompra);
	
	public ResponseEntity<RespSimple> obtenerDetalleComprobanteCompra(Long idComprobanteCompra);

	

	public ResponseEntity<RespSimple> obtenerComprobantesCompra(Long idBodega, Long idPuntoVenta, Date fechaInicial,
			Date fechaFinal);

}
