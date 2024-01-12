package com.facturito.service;

import org.springframework.http.ResponseEntity;

import com.facturito.model.RespSimple;


public interface TFormaPagoComprobantesService {

	public ResponseEntity<RespSimple> getValueFacturasEfectivo(Long idCliente);

	public ResponseEntity<RespSimple> getValueFacturasCredito(Long idCliente, String param);
}
