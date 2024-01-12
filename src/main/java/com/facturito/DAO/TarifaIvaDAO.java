package com.facturito.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturito.entity.TTarifaIva;

public interface TarifaIvaDAO extends JpaRepository<TTarifaIva, Long>{

//	@Query("select t from TTarifaIva t where t.idTarifaIva = :id")
//	public TTarifaIva getTarifa4Id(@Param("id") Long id);
	
	@Query("select t from TTarifaIva t where not t.idTarifaIva =3")
	List<TTarifaIva> enlistarIva();

}
