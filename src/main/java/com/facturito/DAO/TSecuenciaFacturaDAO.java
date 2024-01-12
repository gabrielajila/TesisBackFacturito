package com.facturito.DAO;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturito.entity.TCliente;
import com.facturito.entity.TSecuenciaFactura;

public interface TSecuenciaFacturaDAO extends JpaRepository<TSecuenciaFactura, 	Long> {
	
	public TSecuenciaFactura findByIdCliente (TCliente cliente);

}
