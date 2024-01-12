package com.facturito.service;


import org.springframework.http.ResponseEntity;

import com.facturito.entity.TCliente;
import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;


public interface ClienteService {
	public RespSimple exiteUsuario(TCliente usuario);
//	public RespSimple almacenarCliente(FileSignature fileSignature);
	public String getIdentificacionCliente(String usuario);
	public Long getIdCliente(String usuario);
	public TCliente getCliente(String usuario);
	public RespSimple actualizarCliente(FileSignature fileSignature);
	public String decrypt(String palabra) throws Exception ;
	public String encrypt(String palabra) throws Exception;
	public String obtenerLogo(String ruc);
	public Integer obtenerSecuenciaProducto(Long id_cliente);
	public RespSimple almacenarCliente(FileSignature fileSignature);
	public ResponseEntity<RespSimple> eliminarUsuario(String usuario);

}
