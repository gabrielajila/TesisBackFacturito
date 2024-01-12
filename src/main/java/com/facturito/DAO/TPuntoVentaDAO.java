package com.facturito.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturito.entity.TEstablecimiento;
import com.facturito.entity.TPuntoVenta;


public interface TPuntoVentaDAO extends JpaRepository<TPuntoVenta, Long> {
	public List<TPuntoVenta> findByEstablecimientoAndActivo(TEstablecimiento establecimiento, boolean activo);

	public boolean existsByEstablecimientoAndPuntoEmision(TEstablecimiento establecimiento, String puntoEmision);

	@Query("SELECT pv FROM TPuntoVenta pv WHERE pv.establecimiento.cliente.idCliente = :idCliente")
	TPuntoVenta buscarByIdClienteEstablecimieto(Long idCliente);
}
