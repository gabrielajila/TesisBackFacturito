package com.facturito.service;

import org.springframework.http.ResponseEntity;

import com.facturito.entity.TPuntoVenta;
import com.facturito.model.RespSimple;


public interface PuntoVentaService {

	public ResponseEntity<RespSimple> obtenerPuntosVenta(Long idEstablecimiento);
	
	public ResponseEntity<RespSimple> registrarPuntoVenta(TPuntoVenta puntoVenta);
	
	public ResponseEntity<RespSimple> obtenerEstablecimientos(Long idCliente);
}
