package com.facturito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.DAO.TClienteDAO;
import com.facturito.entity.TCliente;
import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;
import com.facturito.service.ClienteService;
import com.facturito.util.FacturitoEnum;
import com.facturito.util.EnvioCorreo;

@RequestMapping("/cliente")
@RestController
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	TClienteDAO tClienteDAO;

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "existeUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> existeUsuario(@RequestBody TCliente usuario) {
		RespSimple response = clienteService.exiteUsuario(usuario);
		if (response.getError().equals(FacturitoEnum.TRANSACCION_ERROR.getId())) {
			return new ResponseEntity<RespSimple>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "getIdentificacionUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> existeUsuario(@RequestParam(value = "usuario") String usuario) {
		String response = clienteService.getIdentificacionCliente(usuario);
		if (response == null) {
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "getIdUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> idUsuario(@RequestParam(value = "usuario") String usuario) {
		Long response = clienteService.getIdCliente(usuario);
		if (response == null) {
			return new ResponseEntity<Long>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Long>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "getCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TCliente> getCliente(@RequestParam(value = "usuario") String usuario) {
		TCliente cliente = clienteService.getCliente(usuario);
		if (cliente == null) {
			return new ResponseEntity<TCliente>(cliente, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TCliente>(cliente, HttpStatus.OK);
	}

	// MOVIL

//	@PostMapping(value = "registroCliente", produces = MediaType.APPLICATION_JSON_VALUE)
//	public RespSimple registro(@RequestBody FileSignature fileSignature) {
//		return clienteService.almacenarCliente(fileSignature);
//	}

	@PostMapping(value = "actualizarCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	public String actualizarCliente(@RequestBody FileSignature fileSignature) {
		String respuesta;
		try {
			respuesta = clienteService.actualizarCliente(fileSignature).getError();
		} catch (Exception e) {
			respuesta = "1";
		}

		return respuesta;
	}

	@PostMapping(value = "decrypt", produces = MediaType.APPLICATION_JSON_VALUE)
	public String decrypt(@RequestParam(value = "clave") String usuario) throws Exception {
		return clienteService.decrypt(usuario);
	}

	@PostMapping(value = "encrypt", produces = MediaType.APPLICATION_JSON_VALUE)
	public String encrypt(@RequestParam(value = "clave") String usuario) throws Exception {

		return clienteService.encrypt(usuario);
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "logo", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerLogo(@RequestParam(value = "ruc") String ruc) throws Exception {
		return clienteService.obtenerLogo(ruc);
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "sentEmail", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sentEmailContfiables(@RequestParam String correoDesd, @RequestParam String cuerpo) {
		String respuesta;
		try {
			EnvioCorreo env = new EnvioCorreo();
			env.fnNotificarContfiables("info@contfiables.com", correoDesd, "Solicitud de firma electrónica", cuerpo);
			respuesta = "0";
		} catch (Exception e) {
			respuesta = "1";
		}

		return respuesta;
	}

	@PreAuthorize("hasAuthority('ALL_ACCESS')")
	@PostMapping(value = "secuenciaProducto", produces = MediaType.APPLICATION_JSON_VALUE)
	public int obtenerIdTarifaIva(@RequestParam Long idCliente) {
		return clienteService.obtenerSecuenciaProducto(idCliente);
	}

	// =======ELIMINAR USAURIO KEYCLOACK
	@PostMapping(value = "eliminarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> eliminarUsuario(@RequestParam String usuario) {
		return clienteService.eliminarUsuario(usuario);
	}

}
