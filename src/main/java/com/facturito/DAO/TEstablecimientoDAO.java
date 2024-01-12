package com.facturito.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturito.entity.TCliente;
import com.facturito.entity.TEstablecimiento;



public interface TEstablecimientoDAO extends JpaRepository<TEstablecimiento, Long> {
	public List<TEstablecimiento> findByCliente(TCliente cliente);
}
