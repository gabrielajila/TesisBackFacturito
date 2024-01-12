package com.facturito.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facturito.DAO.TPeticionDAO;
import com.facturito.entity.TPeticion;
import com.facturito.service.PeticionService;


@Service
public class PeticionServiceImpl implements PeticionService{

	@Autowired TPeticionDAO peticionDAO;

	@Override
	public TPeticion obtenerPeticionPorNumeroComprobanteYidCliente(String numComprobante, Long idCliente) {
		return peticionDAO.findByNumComprobanteAndIdCliente(numComprobante, idCliente);
	}
	
	
	
}
