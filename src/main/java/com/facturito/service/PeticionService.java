package com.facturito.service;

import com.facturito.entity.TPeticion;

public interface PeticionService {

	public TPeticion obtenerPeticionPorNumeroComprobanteYidCliente(String numComprobante, Long idCliente);
	
}
