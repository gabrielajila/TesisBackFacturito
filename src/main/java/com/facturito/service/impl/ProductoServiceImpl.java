package com.facturito.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.facturito.DAO.TClienteDAO;
import com.facturito.DAO.TCodigosIceDAO;
import com.facturito.DAO.TProductoDAO;
import com.facturito.DAO.TSecuenciaProductoDAO;
import com.facturito.DAO.TarifaIvaDAO;
import com.facturito.entity.TCliente;
import com.facturito.entity.TCodigosIce;
import com.facturito.entity.TProducto;
import com.facturito.entity.TSecuenciaProducto;
import com.facturito.model.RespSimple;
import com.facturito.service.ProductoService;
import com.facturito.util.FacturitoEnum;
import com.facturito.entity.TTarifaIva;
import com.facturito.exception.CustomException;

@Service
public class ProductoServiceImpl implements ProductoService {

	private Logger log = Logger.getLogger(ProductoServiceImpl.class.getName());

	@Autowired
	TProductoDAO productosDAO;

	@Autowired
	TarifaIvaDAO tarifaIvaDAO;

	@Autowired
	TCodigosIceDAO codigosIceDAO;

	@Autowired
	TClienteDAO clienteDAO;

	@Autowired
	TSecuenciaProductoDAO secuenciaProductoDAO;

	// ========MOVIL V1
	@Override
	public List<TProducto> obtenerProductosAPIMOVILV1(Long idCliente, String idProducto) {
		return productosDAO.obtenerProductosMOVILV1(idCliente, idProducto);
	}

//	=============
	@Override
	public List<TProducto> obtenerProductos(Long idCliente, String idProducto) {
		return productosDAO.obtenerProductos(idCliente, idProducto);
	}

	@Override
	public int obtenerIvaProducto(Long idProducto) {
		return productosDAO.obtenerIvaProducto(idProducto);
	}

	@Override
	@Transactional
	public ResponseEntity<RespSimple> guardarProductoCliente(TProducto producto) {
		// TODO Auto-generated method stub
		RespSimple resp = new RespSimple();

		try {
			if (producto.getIdTarifaIva() == null || producto.getIdTarifaIva().getIdTarifaIva() == 0) {
				throw new CustomException("La tarífa es requerída");
			}
			TCliente cliente = clienteDAO.findById(producto.getCliente().getIdCliente()).orElse(null);
			if (cliente == null) {
				resp.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				resp.setMensaje("Cliente no identificado");
				return new ResponseEntity<RespSimple>(resp, HttpStatus.NOT_FOUND);
			}
			TCodigosIce codigoIce = codigosIceDAO.findByCodigoIce(3000l);
			producto.setIdCodigoIce(codigoIce);
			producto.setPrecioUnitario(producto.getPrecioUnitario().setScale(4, RoundingMode.HALF_UP));
			producto.setCodigo(producto.getCodigo().replace(" ", ""));

			if (producto.getActivo() == null) {
				producto.setActivo(true);
			}

			if (producto.getId() == null || producto.getId() == 0) {
				// ========SECUENCIA DEL PRODUCTO=========
				TSecuenciaProducto tSecuenciaProducto = secuenciaProductoDAO.findByIdCliente(cliente);
				
				tSecuenciaProducto.setSecuencial(tSecuenciaProducto.getSecuencial() + 1);
				secuenciaProductoDAO.save(tSecuenciaProducto);
				// ========SECUENCIA DEL PRODUCTO=========
				if (productosDAO.existsByCodigoAndCliente(producto.getCodigo(), cliente)) {
					throw new CustomException(
							"Producto no registrado, ya existe un producto con el código: " + producto.getCodigo());
				}

				producto.setCratedAt(new Date());

				productosDAO.save(producto);
				resp.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
				resp.setMensaje("Producto registrado correctamente");
				return new ResponseEntity<RespSimple>(resp, HttpStatus.CREATED);

			} else {
				TProducto existeProductoBD = productosDAO.findByCodigoAndCliente(producto.getCodigo(), cliente);
				if (existeProductoBD != null) {
					if (existeProductoBD.getId().compareTo(producto.getId()) != 0) {
						throw new CustomException(
								"Producto no registrado, ya existe un producto con el código: " + producto.getCodigo());
					}

				}

				TProducto tProducto = productosDAO.findById(producto.getId()).orElse(null);
				if (tProducto == null) {
					throw new CustomException("Error al encontrar el producto solicitado.");
				}

				tProducto.setUpdatedAt(new Date());

				tProducto.setCodigo(producto.getCodigo());
				tProducto.setInformacionAdicional(producto.getInformacionAdicional());
				tProducto.setNombreProducto(producto.getNombreProducto());
				tProducto.setPrecioUnitario(producto.getPrecioUnitario().setScale(4, RoundingMode.HALF_UP));

				tProducto.setActivo(producto.isActivo());
				tProducto.setCliente(cliente);
				// ACTUALIZAR
				productosDAO.save(tProducto);
				resp.setMensaje("Producto actualizado correctamente");
				resp.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
				return new ResponseEntity<RespSimple>(resp, HttpStatus.CREATED);
			}

		} catch (CustomException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.warning("Error al actualizar producto");
			System.out.println("Error: " + e.getMessage());
			resp.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			resp.setMensaje(e.getMessage());
			return new ResponseEntity<RespSimple>(resp, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.warning("Error al guardar producto");
			System.out.println("Error de guardar producto: " + e.getMessage());
			resp.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			resp.setMensaje("Error al guardar el producto.");
			return new ResponseEntity<RespSimple>(resp, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<TTarifaIva> enlistarIva() {
		return tarifaIvaDAO.enlistarIva();
	}

	@Override
	public TProducto obtenerProducto(Long idProducto) {
		return productosDAO.findById(idProducto).get();
	}

	@Override
	public String obtenerNombreProducto(Long idProducto) {
		return productosDAO.obtenerNombreProducto(idProducto);
	}

	@Override
	public String obtenerCodigoProducto(Long idProducto) {
		return productosDAO.obtenerCodigoProducto(idProducto);
	}

	@Override
	public BigDecimal obtenerPrecioProducto(Long idProducto) {
		return productosDAO.obtenerPrecioProducto(idProducto);
	}

	@Override
	public Long obtenerIdTarifaIva(Long idProducto) {
		return productosDAO.obtenerIdTarifaIva(idProducto);
	}

	@Override
	public Page<TProducto> obtenerProductosFront(Long idCliente) {
		Page<TProducto> pages = new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0);

		try {
			Pageable pageable = PageRequest.of(1, 10);
			return productosDAO.obtenerProductosFront(idCliente, pageable);
		} catch (DataAccessException e) {
			// TODO: handle exception
			System.out.println(e.getMostSpecificCause().getMessage());
			return pages;
		}
	}

	@Override
	public List<TProducto> filtrarProductosByQuery(Long idCliente, String query) {
		return productosDAO.filtrarProductosQuery(idCliente, query.toLowerCase());
	}

	@Override
	public List<TProducto> filtrarProductosNoInventariablesByQuery(Long idCliente, String query) {
		return productosDAO.filtrarProductosNoInventariables(idCliente, query.toLowerCase());
	}

	@Override
	public ResponseEntity<RespSimple> getProductoByCode(String codigo, Long idBodega, Long idCliente) {
		RespSimple response = new RespSimple();

		try {
			TProducto producto = productosDAO.findByCodigoAndClienteAndActivo(codigo, new TCliente(idCliente), true);
			if (producto == null) {
				response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				response.setMensaje("No existe el producto solicitado");
				response.setDescripcion("");
				return new ResponseEntity<RespSimple>(response, HttpStatus.NOT_FOUND);
			}

			response.setMensaje("producto obtenido");
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setDescripcion("noivn");
			response.setData(producto);
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
			response.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			response.setMensaje("Error al obtener el producto solicitado");
			response.setDescripcion("");
			return new ResponseEntity<RespSimple>(response, HttpStatus.BAD_REQUEST);
		}

	}
}
