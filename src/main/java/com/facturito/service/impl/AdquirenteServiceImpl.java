package com.facturito.service.impl;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facturito.DAO.TAdquirenteDAO;
import com.facturito.entity.TAdquiriente;
import com.facturito.model.RespSimple;
import com.facturito.service.AdquirienteService;
import com.facturito.util.FacturitoEnum;



@Service
public class AdquirenteServiceImpl implements AdquirienteService {

	@Autowired
	TAdquirenteDAO adquirenteDao;

	private Logger log = Logger.getLogger(AdquirenteServiceImpl.class.getName());

	@Override
	public ResponseEntity<RespSimple> getAdquiriente4identification(String identificacion) {
		RespSimple response=new RespSimple();
		try {
			TAdquiriente adquiriente = adquirenteDao.findByIdentificacion(identificacion);
			if (adquiriente==null) {
				response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				response.setMensaje("El cliente con la identificación: "+identificacion+" no encontrado");
				return new ResponseEntity<RespSimple>(response, HttpStatus.NOT_FOUND);
			}
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("Cliente encontrado");
			response.setData(adquiriente);
			System.out.println("ad: "+adquiriente);
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL GET ADQUIRIENTE ", e);
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Error al obtener el cliente solicitado");
			return new ResponseEntity<RespSimple>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public Boolean existeAdquiriente(String identificacion) {
		return adquirenteDao.existsByIdentificacion(identificacion);
	}

	@Override
	public ResponseEntity<RespSimple> filtrarAdquiriente(String q) {
		RespSimple response = new RespSimple();
		try {
			response.setCodigo("0");
			response.setMensaje("lista obtenida");
			response.setData(adquirenteDao.buscarPorRazonSocialORIdentifiacion(q.toLowerCase()));
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo("1");
			response.setMensaje("Error en la lista de clientes:");
			response.setData(new ArrayList<>());
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<RespSimple> guardarAdquiriente(TAdquiriente adquiriente) {
		RespSimple response = new RespSimple();
		try {
			response.setCodigo("0");
			response.setMensaje("guardado");
			boolean existe =adquirenteDao.existsByIdentificacion(adquiriente.getIdentificacion());
			if (existe) {
				response.setCodigo("1");
				response.setMensaje("Ya existe un cliente registrado con esa identificación");
				return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
			}
			response.setData(adquirenteDao.save(adquiriente));
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setCodigo("1");
			response.setMensaje("Error al guardar el cliente");
			response.setData(new ArrayList<>());
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
