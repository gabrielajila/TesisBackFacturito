package com.facturito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.DAO.TClienteDAO;
import com.facturito.auth.AuthenticationRequest;
import com.facturito.auth.AuthenticationResponse;
import com.facturito.auth.AuthenticationService;
import com.facturito.entity.TCliente;
import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;
import com.facturito.service.ClienteService;
import com.facturito.util.FacturitoEnum;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private TClienteDAO clienteDAO;

	@Autowired
	ClienteService clienteService;

	@Autowired
	private AuthenticationService authenticationService;

	@PreAuthorize("permitAll")
	@PostMapping("/authenticate")
	public ResponseEntity<RespSimple> login(@RequestBody @Valid AuthenticationRequest authRequest) {
		try {
			AuthenticationResponse jwtDto = authenticationService.login(authRequest);
			TCliente cliente = clienteDAO.getCliente(authRequest.getUsername());
			return new ResponseEntity<RespSimple>(new RespSimple(FacturitoEnum.TRANSACCION_OK.getId(),
					"Autenticación correcta", cliente, jwtDto.getJwt()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RespSimple>(
					new RespSimple(FacturitoEnum.TRANSACCION_ERROR.getId(), "Error al autenticarse", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PreAuthorize("permitAll")
	@GetMapping("/public-access")
	public String publicAccessEndpoint() {
		return "este endpoint es público";
	}

	@PostMapping(value = "registro", produces = MediaType.APPLICATION_JSON_VALUE)
	public RespSimple completarRegistro(@RequestBody FileSignature fileSignature) {
		return clienteService.almacenarCliente(fileSignature);
	}

	@PostMapping("login")
	public ResponseEntity<RespSimple> login(@RequestParam String usuario, @RequestParam String password) {
		RespSimple response = new RespSimple();
		response.setData(clienteDAO.findById(2l));
		response.setCodigo("0");
		response.setMensaje("mensaje");
		System.out.println("response: " + response.getData());
		return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
	}

}
