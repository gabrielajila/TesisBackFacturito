package com.facturito.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturito.entity.TCliente;



public interface TClienteDAO extends JpaRepository<TCliente, Long> {

	@Query("select c from TCliente c where user = :usuario")
	public TCliente getCliente(@Param("usuario") String usuario);
	
	@Query("select c.idCliente from TCliente c where user = :usuario")
	public Long getIdCliente(@Param("usuario") String usuario);
	
	@Query("select c from TCliente c where identification = :identificacion")
	public TCliente getCliente4identificacion(@Param("identificacion") String identificacion);
	
	public Boolean existsByIdentificationOrUser(String identificacion, String username);
	
	@Query("select c.identification from TCliente c where user = :usuario")
	public String getIdentificacionCliente(@Param("usuario") String usuario);
	
	Optional<TCliente> findByUser(String username);
}
