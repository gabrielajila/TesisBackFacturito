package com.facturito.DAO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturito.consultas.DashboardClienteProductoFrecuenteDTO;
import com.facturito.entity.TFacturaDetalle;

public interface TFacturaDetalleDAO extends JpaRepository<TFacturaDetalle, Long> {

	@Query("select t from TFacturaDetalle t where t.idFactura.id =:idFactura")
	public List<TFacturaDetalle> buscarDetalleByIdFactura(@Param("idFactura") Long idFactura);

	@Query("SELECT td.idProducto.nombreProducto as nombre, count(td.idProducto.id) as cantidad FROM TFacturaDetalle td WHERE td.idFactura.idCliente.idCliente = :idCliente GROUP BY td.idProducto.nombreProducto ORDER BY cantidad desc")
	public List<DashboardClienteProductoFrecuenteDTO> getProductosFrecuentes(Long idCliente, Pageable pageable);
}
