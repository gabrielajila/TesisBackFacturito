package com.facturito.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturito.entity.TFormaPago;


public interface TFormaPagoDAO extends JpaRepository<TFormaPago, Long> {
	
	@Query ("select f from TFormaPago f where f.id = :codigo")
	public TFormaPago getFormaPago4Id(@Param("codigo") Integer codigo );
	
	public TFormaPago findByCodigoSri(String codigo);

}
