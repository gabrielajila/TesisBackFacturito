package com.facturito.service;

import org.springframework.http.ResponseEntity;

import com.facturito.model.RespSimple;


public interface FormaPagoService {

	public ResponseEntity<RespSimple> obtenerFormasPago();
	
}
