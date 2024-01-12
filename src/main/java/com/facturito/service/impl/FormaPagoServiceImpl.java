package com.facturito.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facturito.model.RespSimple;
import com.facturito.service.FormaPagoService;
import com.facturito.util.FacturitoEnum;
import com.facturito.DAO.TFormaPagoDAO;
import com.facturito.entity.TFormaPago;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {

	@Autowired
	private TFormaPagoDAO formaPagoDAO;

	@Override
	public ResponseEntity<RespSimple> obtenerFormasPago() {
		RespSimple response = new RespSimple();
		List<TFormaPago> formasPago = formaPagoDAO.findAll();
		response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
		response.setMensaje("lista formas de pago obtenida");
		response.setData(formasPago);
		return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
	}

}
