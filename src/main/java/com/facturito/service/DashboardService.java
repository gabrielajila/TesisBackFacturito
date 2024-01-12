package com.facturito.service;

import org.springframework.http.ResponseEntity;

import com.facturito.model.RespSimple;

public interface DashboardService {

	public ResponseEntity<RespSimple> getFacturasMeses(Long idCliente);	
}
