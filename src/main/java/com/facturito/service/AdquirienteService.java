package com.facturito.service;

import org.springframework.http.ResponseEntity;

import com.facturito.entity.TAdquiriente;
import com.facturito.model.RespSimple;

public interface AdquirienteService {

	public ResponseEntity<RespSimple> getAdquiriente4identification(String identificacion);
	
	public ResponseEntity<RespSimple> filtrarAdquiriente(String q);
	public ResponseEntity<RespSimple> guardarAdquiriente(TAdquiriente tadquiriente);
	
	public Boolean existeAdquiriente(String identificacion);
}
