package com.facturito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;
import com.facturito.service.FirmaElectronicaService;

@RestController
public class FirmaElectronicaController {

	@Autowired
	FirmaElectronicaService firmaElectronicaService;
	
	@PostMapping(value = "almacenarArchivo", produces = MediaType.APPLICATION_JSON_VALUE)
	public RespSimple almacenarArchivo (@RequestBody FileSignature fileSignature ) {
		RespSimple banderaAlmacenar = new RespSimple();
		banderaAlmacenar = firmaElectronicaService.almacenarArchivo(fileSignature);
		return banderaAlmacenar;
	}

}
