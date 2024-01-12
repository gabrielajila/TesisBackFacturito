package com.facturito.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturito.FacturaDTO;
import com.facturito.consultas.DashboardFacturaAnioMes;
import com.facturito.entity.TCliente;
import com.facturito.entity.TFactura;

public interface TFacturaDAO extends JpaRepository<TFactura, Long> {

	@Query("SELECT NEW com.facturito.FacturaDTO(tf.id, tf.idAdquiriente.razonSocial, tf.fechaEmision, tf.total, tf.authorizacion, tf.numComprobante) FROM TFactura tf WHERE tf.idCliente.idCliente = :idCliente ORDER BY tf.id desc")
	public List<FacturaDTO> obtenerUltimas3Facturas(@Param("idCliente") Long idCliente, Pageable pageable);

	@Query("SELECT NEW com.facturito.FacturaDTO(tf.id, tf.idAdquiriente.razonSocial, tf.fechaEmision, tf.total, tf.authorizacion, tf.numComprobante) FROM TFactura tf WHERE tf.idCliente.idCliente = :idCliente AND tf.isAutorizado = TRUE AND tf.fechaEmision BETWEEN :fechaInicio AND :fechaFinal ORDER BY tf.id desc")
	public List<FacturaDTO> obtenerFacturasAutorizadasPorFecha(Long idCliente, Date fechaInicio, Date fechaFinal);

	public List<TFactura> findByIdClienteAndNumComprobante(TCliente cliente, String numComprobante);

	@Query(value = "select t from TFactura t where t.idCliente.idCliente =:idCliente ORDER BY t.id DESC")
	public List<TFactura> obtenerFacturas(@Param("idCliente") Long idCliente);

	@Query(value = "select * from t_factura tf where tf.id_adquiriente = (select ta.id_adquiriente from t_adquiriente ta where ta.identificacion=:identificacion) and tf.id_cliente =:idCliente and tf.fecha_emision between TO_TIMESTAMP(:fechaInicio ,'YYYY-MM-DD HH24:MI:SS') and TO_TIMESTAMP(:fechaFinal ,'YYYY-MM-DD HH24:MI:SS') and tf.is_autorizado = true ORDER BY id_factura  DESC", nativeQuery = true)
	public List<TFactura> obtenerFacturasPorFechaEIdentificacion(@Param("idCliente") int idCliente,
			@Param("fechaInicio") String fechaInicio, @Param("fechaFinal") String fechaFinal,
			@Param("identificacion") String identificacion);

//	@Query(value = "select t from TFactura t where t.idCliente.idCliente =:idCliente and t.isAutorizado =true ORDER BY t.id DESC")
//	public List<TFactura> obtenerFacturas(@Param("idCliente") BigDecimal idCliente);

	@Query(value = "select t from TFactura t where t.numComprobante =:numComprobante and t.idCliente.idCliente =:idCliente ")
	public TFactura obtenerFacturaPorNumComprobante(@Param("numComprobante") String numComprobante,
			@Param("idCliente") Long idCliente);

	@Query(value = "select * from t_factura tf where tf.id_cliente =:idCliente and tf.fecha_emision between TO_TIMESTAMP(:fechaInicio ,'YYYY-MM-DD HH24:MI:SS') and TO_TIMESTAMP(:fechaFinal ,'YYYY-MM-DD HH24:MI:SS') and tf.is_autorizado =:isAutorizado ORDER BY id_factura  DESC", nativeQuery = true)
	public List<TFactura> obtenerFacturasNoAutorizadasPorFechaIdentificacion(@Param("idCliente") int idCliente,
			@Param("fechaInicio") String fechaInicio, @Param("fechaFinal") String fechaFinal,
			@Param("isAutorizado") boolean isAutorizado);

	public TFactura findByNumComprobanteAndIdCliente(String numComprobante, TCliente cliente);

	public TFactura findByAuthorizacion(String autorizacion);

	public List<TFactura> findByIsAutorizado(Boolean isAutorizado);

	public List<TFactura> findByIsAutorizadoAndEnviada(Boolean idAutorizado, Boolean Enviado);

	// ===================REPORTES DASHBOARD===============
	@Query("SELECT COUNT(f) FROM TFactura f WHERE f.idCliente.idCliente =:idCliente AND YEAR(f.fechaEmision) = :anio")
	public Long countByIdClienteAndAnio(Long idCliente, int anio);

	@Query("SELECT SUM(f.total) FROM TFactura f WHERE f.idCliente.idCliente =:idCliente AND YEAR(f.fechaEmision) = :anio")
	public BigDecimal sumByIdClienteAndAnio(Long idCliente, int anio);
	
	@Query("SELECT EXTRACT(YEAR FROM tf.fechaEmision) AS anio, EXTRACT(MONTH FROM tf.fechaEmision) AS mes, COUNT(tf.id) AS cantidad FROM TFactura tf WHERE tf.idCliente.idCliente= :idCliente AND tf.fechaEmision >= :fechaHaceSeisMeses GROUP BY EXTRACT(YEAR FROM tf.fechaEmision), EXTRACT(MONTH FROM tf.fechaEmision) ORDER BY mes, anio ASC")
	public List<DashboardFacturaAnioMes> getFacturasLast6Mont(Long idCliente, Date fechaHaceSeisMeses, Pageable pageable);
//	@Query("SELECT NEW ec.tws2.back.contfiables.contultasModels.DashboardFacturaAnioMes( f.id, f.fechaEmision, f.total) FROM TFactura f WHERE f.idCliente.idCliente =:idCliente AND YEAR(f.fechaEmision) = :anio")
//	public List<DashboardFacturaAnioMes> buscarFacturasByClienteAndAnio(Long idCliente, int anio);
//
//	@Query("SELECT NEW ec.tws2.back.contfiables.contultasModels.DashboardFacturaAnioMes( f.id, f.fechaEmision, f.total) FROM TFactura f WHERE f.idCliente.idCliente =:idCliente AND YEAR(f.fechaEmision) = :anio AND MONTH(f.fechaEmision) = :mes")
//	public List<DashboardFacturaAnioMes> buscarFacturasByClienteAndAnioAndMes(Long idCliente, int anio, int mes);

}
