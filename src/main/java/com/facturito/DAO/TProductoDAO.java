package com.facturito.DAO;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturito.entity.TCliente;
import com.facturito.entity.TProducto;


public interface TProductoDAO extends JpaRepository<TProducto, Long> {
	
	@Query(value = "select p.* from t_producto p where p.id_cliente = :idCliente and cast(p.id_producto as varchar) NOT IN (SELECT unnest(string_to_array(:idProducto, ',')))", nativeQuery = true)
	public List<TProducto> obtenerProductosMOVILV1(@Param("idCliente") Long idCliente, @Param("idProducto") String idProducto);
	
	@Query(value = "select p.* from t_producto p where p.id_cliente = :idCliente AND  p.activo=true and cast(p.id_producto as varchar) NOT IN (SELECT unnest(string_to_array(:idProducto, ',')))", nativeQuery = true)
	public List<TProducto> obtenerProductos(@Param("idCliente") Long idCliente, @Param("idProducto") String idProducto);

	@Query("select p from TProducto p where p.cliente.idCliente = :idCliente")
	public Page<TProducto> obtenerProductosFront(@Param("idCliente") Long idCliente, Pageable pageable);

	//@Query("select p from TProducto p where p.cliente.idCliente = :idCliente AND p.inventariable=false AND p.activo=true AND (LOWER(p.codigo) LIKE %:q% OR LOWER(p.nombreProducto) LIKE %:q%)")
@Query("SELECT p FROM TProducto p WHERE p.cliente.idCliente = :idCliente AND p.activo = true AND (LOWER(p.codigo) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :q, '%')))")
	public List<TProducto> filtrarProductosNoInventariables(@Param("idCliente") Long idCliente, @Param("q") String q);

	@Query("select p.nombreProducto from TProducto p where p.id=:id")
	public String obtenerNombreProducto(@Param("id") Long idProducto);

	@Query("select p.codigo from TProducto p where p.id=:id")
	public String obtenerCodigoProducto(@Param("id") Long idProducto);

	@Query("select p.precioUnitario from TProducto p where p.id=:id")
	public BigDecimal obtenerPrecioProducto(@Param("id") Long idProducto);
	
	@Query("select p.idTarifaIva.idTarifaIva from TProducto p where p.id=:id")
	public Long obtenerIdTarifaIva(@Param("id") Long idProducto);

	@Query("select p.idTarifaIva.porcentaje from TProducto p where p.id =:id")
	public Integer obtenerIvaProducto(@Param("id") Long idProducto);

	
	@Query("SELECT ta FROM TProducto ta")
	public List<TProducto> listarProducto();

	@Query("SELECT p FROM TProducto p where p.cliente.idCliente = :idCliente AND LOWER(p.codigo) LIKE %:q% OR LOWER(p.nombreProducto) LIKE %:q%")
	public List<TProducto> filtrarProductosQuery(Long idCliente, String q);
	
	public boolean existsByCodigoAndCliente(String codigo, TCliente cliente);
	
	public TProducto findByCodigoAndCliente(String codigo, TCliente cliente);
	
	public TProducto findByCodigoAndClienteAndActivo(String codigo, TCliente cliente, Boolean activo);

	//List<TProducto> findByClienteAndInventariable(TCliente cliente, boolean inventariable);
	
}
