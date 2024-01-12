package com.facturito.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facturito.DAO.TFormaPagoComprobantesDAO;
import com.facturito.model.RespSimple;
import com.facturito.service.TFormaPagoComprobantesService;
import com.facturito.util.FacturitoEnum;

@Service
public class FormaPagoComprobanteServiceImpl implements TFormaPagoComprobantesService {

	@Autowired
	private TFormaPagoComprobantesDAO formaPagoComprobantesDAO;

	@Override
	public ResponseEntity<RespSimple> getValueFacturasEfectivo(Long idCliente) {
		RespSimple response = new RespSimple();
		try {
			response.setData(formaPagoComprobantesDAO.buscarFacturasEfectivo(idCliente));
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Valor de las facturas con la forma de pago 'EFECTIVO'");
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setDescripcion(e.getMessage());
			response.setMensaje("Error al obtener el valor de las facturas con la forma de pago 'EFECTIVO'");
			return new ResponseEntity<RespSimple>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<RespSimple> getValueFacturasCredito(Long idCliente, String param) {
		RespSimple response = new RespSimple();
		try {
			response.setData(formaPagoComprobantesDAO.buscarFacturasConCreditoActivo(idCliente));
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Valor de las facturas con la forma de pago 'CREDITO'");
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setDescripcion(e.getMessage());
			response.setMensaje("Error al obtener el valor de las facturas con la forma de pago 'CREDITO'");
			return new ResponseEntity<RespSimple>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
