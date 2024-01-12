package com.facturito.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturito.entity.TAdquiriente;

public interface TAdquirenteDAO extends JpaRepository<TAdquiriente, Long> {

//	@Query("select a from TAdquiriente a where identificacion = :identificacion")
//	public TAdquiriente getAdquiriente(@Param("identificacion") String identificacion);

	TAdquiriente findByIdentificacion(String identificacion);

	Boolean existsByIdentificacion(String identificacion);

	@Query("SELECT ta FROM TAdquiriente ta WHERE ta.identificacion <> '9999999999999' AND (LOWER(ta.razonSocial) LIKE %:q% OR ta.identificacion LIKE %:q%)")
	public List<TAdquiriente> buscarPorRazonSocialORIdentifiacion(String q);

	@Query("SELECT ta FROM TAdquiriente ta")
	public List<TAdquiriente> listarTodos();
}
