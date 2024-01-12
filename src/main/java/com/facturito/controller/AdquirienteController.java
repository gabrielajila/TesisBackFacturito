package com.facturito.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.DAO.TAdquirenteDAO;
import com.facturito.DAO.TTipoAdquirienteDAO;
import com.facturito.DAO.TTipoIdentificacionDAO;
import com.facturito.entity.TAdquiriente;
import com.facturito.entity.TTipoIdentificacion;
import com.facturito.model.RespSimple;
import com.facturito.service.AdquirienteService;

@RequestMapping("adquiriente")
@RestController
public class AdquirienteController {

	@Autowired
	AdquirienteService adquirienteService;
	@Autowired
	TTipoIdentificacionDAO tipoIdentificacionDAO;
	@Autowired
	TAdquirenteDAO adquirienteDAO;

	Logger log = Logger.getLogger(AdquirienteController.class.getName());

	@PostMapping(value = "getAdquiriente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> getAdquiriente(@RequestParam(value = "identificacion") String identificacion) {
		return adquirienteService.getAdquiriente4identification(identificacion);
	}

	@PostMapping(value = "/listaTiposIdentificacion", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TTipoIdentificacion> enlistarTipoIdentificacion() {
		return tipoIdentificacionDAO.enlistarTipoIdentificacion();
	}

	@PostMapping(value = "/filtrarAdquirienteQuery", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> filtrarAdquirienteQuery(@RequestParam("q") String q) {
		return adquirienteService.filtrarAdquiriente(q);
	}
	
	@PostMapping(value = "/guardarAdquiriente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> save(@RequestBody TAdquiriente tadquiriente ) {
		return adquirienteService.guardarAdquiriente(tadquiriente);
	}

	@PostMapping(value = "/listarAdquiriente", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TAdquiriente> listarTodos()  {
		return adquirienteDAO.listarTodos();
	}
}
