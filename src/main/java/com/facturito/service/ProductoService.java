package com.facturito.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.facturito.entity.TProducto;
import com.facturito.entity.TTarifaIva;
import com.facturito.model.RespSimple;

public interface ProductoService {
	
	public List<TProducto> obtenerProductosAPIMOVILV1(Long idCliente, String idProducto);

	public List<TProducto> obtenerProductos(Long idCliente, String idProducto);

	public Page<TProducto> obtenerProductosFront(Long idCliente);

	public ResponseEntity<RespSimple> guardarProductoCliente(TProducto producto);

	public List<TTarifaIva> enlistarIva();
	public int obtenerIvaProducto(Long idProducto);
//	public BigDecimal getIdProductoCliente(BigDecimal idUsuario);

	public TProducto obtenerProducto(Long idProducto);

	public String obtenerNombreProducto(Long idProducto);

	public String obtenerCodigoProducto(Long idProducto);

	public BigDecimal obtenerPrecioProducto(Long idProducto);

	public List<TProducto> filtrarProductosByQuery(Long idCliente, String query);

	public List<TProducto> filtrarProductosNoInventariablesByQuery(Long idCliente, String query);

	public Long obtenerIdTarifaIva(Long idProducto);

	public ResponseEntity<RespSimple> getProductoByCode(String codigo, Long idBodega, Long idCliente);
	

}
