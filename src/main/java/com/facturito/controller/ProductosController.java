package com.facturito.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.DAO.TProductoDAO;
import com.facturito.entity.TProducto;
import com.facturito.entity.TTarifaIva;
import com.facturito.model.RespSimple;
import com.facturito.service.ProductoService;


@RestController
@RequestMapping("producto")
public class ProductosController {

	@Autowired
	ProductoService productosService;
	
	@Autowired
	TProductoDAO productoDAO;


	@PostMapping(value = "getProductoByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> getProductoByCode(@RequestParam("codigo") String codigo,
			@RequestParam("idCliente") Long idCliente, @RequestParam("idBodega") Long idBodega) {
		return productosService.getProductoByCode(codigo, idBodega, idCliente);
	}

	// ====== MOVIL =======

	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TProducto> obtenerProductosMovil(@RequestParam("idCliente") Long idCliente,
			@RequestParam("idProducto") String idProducto) {
		return productosService.obtenerProductosAPIMOVILV1(idCliente, idProducto);
	}

	//WEB
	@PostMapping(path = "/listprod", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TProducto> obtenerProductosFront(@RequestParam("idCliente") Long idCliente) {
		return productosService.obtenerProductosFront(idCliente);
	}

	@PostMapping(value = "/guardarProducto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> guardarProducto(@RequestBody TProducto producto) {
		ResponseEntity<RespSimple> resp = productosService.guardarProductoCliente(producto);
		return resp;
	}

	@PostMapping(value = "/listaIva", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TTarifaIva> enlistarIva() {
		return productosService.enlistarIva();
	}

	@PostMapping(value = "/producto", produces = MediaType.APPLICATION_JSON_VALUE)
	public TProducto obtenerProducto(@RequestParam("idProducto") Long idProducto) {
		return productosService.obtenerProducto(idProducto);
	}

	@PostMapping(value = "/producto/nombre", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerNombreProducto(@RequestParam("idProducto") Long idProducto) {
		return productosService.obtenerNombreProducto(idProducto);
	}

	@PostMapping(value = "/producto/codigo", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerCodigoProducto(@RequestParam("idProducto") Long idProducto) {
		return productosService.obtenerCodigoProducto(idProducto);
	}

	@PostMapping(value = "/producto/precio", produces = MediaType.APPLICATION_JSON_VALUE)
	public BigDecimal obtenerPrecioProducto(@RequestParam("idProducto") Long idProducto) {
		return productosService.obtenerPrecioProducto(idProducto).setScale(2, RoundingMode.HALF_UP);
	}

	@PostMapping(value = "/producto/iva", produces = MediaType.APPLICATION_JSON_VALUE)
	public int obtenerIvaProducto(@RequestParam("idProducto") Long idProducto) {
		return productosService.obtenerIvaProducto(idProducto);
	}

	/*@PostMapping(value = "/getProductoInventario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespSimple> getProductoInventario(
			@RequestParam("idProductoInventario") Long idProductoInventario) {
		return productosService.getProductoInventarioByIdProducto(idProductoInventario);
	}*/

	@PostMapping(value = "/producto/calcularMonto", produces = MediaType.APPLICATION_JSON_VALUE)
	public BigDecimal obtenerIdTarifaIva(@RequestParam double precio, @RequestParam double iva,
			@RequestParam double descuento) {

		BigDecimal descuentoPrecioT = BigDecimal.valueOf(precio)
				.subtract(BigDecimal.valueOf(precio)
						.multiply(BigDecimal.valueOf(descuento).divide(new BigDecimal("100"))))
				.setScale(2, RoundingMode.HALF_UP);
		BigDecimal ivaPrecioT = BigDecimal.valueOf(iva).divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
		return descuentoPrecioT.add(descuentoPrecioT.multiply(ivaPrecioT)).setScale(2, RoundingMode.HALF_UP);
	}

	@PostMapping(value = "/filtrarProductos", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TProducto> filtrarProductosByQuery(@RequestParam("idCliente") Long idCliente,
			@RequestParam("query") String query) {
		return productosService.filtrarProductosByQuery(idCliente, query);
	}

	@PostMapping(value = "/filtrarProductosNoInventariables", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TProducto> filtrarProductosNoInventariables(@RequestParam("idCliente") Long idCliente,
			@RequestParam("query") String query) {
		return productosService.filtrarProductosNoInventariablesByQuery(idCliente, query);
	}
	
	@PostMapping(value = "/producto/idIva", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long obtenerIdTarifaIva(@RequestParam("idProducto") Long idProducto) {
		Long id=productosService.obtenerIdTarifaIva(idProducto);

		return id;
	}


	/*@PostMapping(value = "/filtrarProductosInventariables", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TInventario> filtrarProductosInventariablesByQuery(@RequestParam("idBodega") Long idBodega,
			@RequestParam("idCliente") Long idCliente, @RequestParam("query") String query) {
		return productosService.filtrarProductosInventariablesByQuery(idBodega, idCliente, query);
	}

	@PostMapping(value = "/filtrarProductosInventariablesConStock", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TInventario> filtrarProductosInventariablesConStock(@RequestParam("idBodega") Long idBodega,
			@RequestParam("idCliente") Long idCliente, @RequestParam("query") String query) {
		return productosService.filtrarProductosInventariablesByQueryFacturacion(idBodega, idCliente, query);
	}*/

}
