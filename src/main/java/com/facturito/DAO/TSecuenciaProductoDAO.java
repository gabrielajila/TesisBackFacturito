package com.facturito.DAO;



import org.springframework.data.jpa.repository.JpaRepository;

import com.facturito.entity.TCliente;
import com.facturito.entity.TSecuenciaProducto;

public interface TSecuenciaProductoDAO extends JpaRepository<TSecuenciaProducto, Long> {
	public TSecuenciaProducto findByIdCliente(TCliente cliente);
}
