package com.facturito.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturito.entity.TPeticion;


public interface TPeticionDAO extends JpaRepository<TPeticion, Long>{
	
	public TPeticion findByNumComprobanteAndIdCliente(String numComprobante, Long idCliente);
}
