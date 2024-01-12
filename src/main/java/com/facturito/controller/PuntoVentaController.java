package com.facturito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.entity.TPuntoVenta;
import com.facturito.model.RespSimple;
import com.facturito.service.PuntoVentaService;


@RestController
@RequestMapping("puntoVenta/")
public class PuntoVentaController {

	@Autowired
	private PuntoVentaService puntoVentaService;

	@PostMapping(value = "getPuntosVenta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> obtenerPuntosVenta(@RequestParam("idEstablecimiento") Long idEstablecimiento) {
		return puntoVentaService.obtenerPuntosVenta(idEstablecimiento);
	}

	@PostMapping(value = "registrarPuntoVenta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> registrarPuntoVenta(@RequestBody TPuntoVenta puntoVenta) {
		return puntoVentaService.registrarPuntoVenta(puntoVenta);
	}
	
	@PostMapping(value = "getEstablecimientos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> getEstablecimientos(@RequestParam Long idCliente) {
		return puntoVentaService.obtenerEstablecimientos(idCliente);
	}

}
