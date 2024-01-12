package com.facturito.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturito.DAO.TEstablecimientoDAO;
import com.facturito.DAO.TPuntoVentaDAO;
import com.facturito.entity.TCliente;
import com.facturito.entity.TEstablecimiento;
import com.facturito.entity.TPuntoVenta;
import com.facturito.model.RespSimple;
import com.facturito.util.FacturitoEnum;

@Service
public class PuntoVentaServiceImpl implements com.facturito.service.PuntoVentaService {
	@Autowired
	private TPuntoVentaDAO puntoVentaDAO;

	@Autowired
	private TEstablecimientoDAO establecimientoDAO;

	@Override
	public ResponseEntity<RespSimple> obtenerPuntosVenta(Long idEstableciemiento) {
		RespSimple response = new RespSimple();
		TEstablecimiento establecimiento = establecimientoDAO.findById(idEstableciemiento).orElse(null);
		if (establecimiento == null) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Los establecimientos del cliente solicitado no se encontrarón.");
			return new ResponseEntity<RespSimple>(response, HttpStatus.NOT_FOUND);
		}
		List<TPuntoVenta> listaPuntosVenta = puntoVentaDAO.findByEstablecimientoAndActivo(establecimiento, true);
		response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
		response.setMensaje("Lista de puntos de venta obtenido");
		response.setData(listaPuntosVenta);
		return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<RespSimple> registrarPuntoVenta(TPuntoVenta puntoVenta) {
		RespSimple response = new RespSimple();

		if (puntoVenta.getIdPuntoVenta() != null) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Este registro ya contiene un identificador, no se puede registrar.");
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			System.out.println("id establecimiento: "
					+ establecimientoDAO.findById(puntoVenta.getEstablecimiento().getIdEstablecimiento()).orElse(null));
			TEstablecimiento establecimiento = establecimientoDAO
					.findById(puntoVenta.getEstablecimiento().getIdEstablecimiento()).orElse(null);
			if (establecimiento == null) {
				response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				response.setMensaje("Establecimiento del cliente solicitado no se encontró.");
				return new ResponseEntity<RespSimple>(response, HttpStatus.NOT_FOUND);
			}

			boolean existe = puntoVentaDAO.existsByEstablecimientoAndPuntoEmision(establecimiento,
					puntoVenta.getPuntoEmision());
			if (existe) {
				response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				response.setMensaje("Ya existe un punto de venta con esos datos.");
				return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
			}
			// REGISTRAR EL PUNTO DE VENTA Y POSTERIOR INICIAR LA SECUENCIA DE COMPROBANTES
			TPuntoVenta puntoDB = puntoVentaDAO.save(puntoVenta);

			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("Punto de venta creado.");
			return new ResponseEntity<RespSimple>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Error al registrar el punto de emisión.");
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<RespSimple> obtenerEstablecimientos(Long idCliente) {
		RespSimple response = new RespSimple();
		response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
		response.setMensaje("lista obtenida");
		List<TEstablecimiento> establecimientos = establecimientoDAO.findByCliente(new TCliente(idCliente));
		response.setData(establecimientos);
		return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
	}

}
