package com.facturito.DAO;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturito.entity.TTipoIdentificacion;


public interface TTipoIdentificacionDAO extends JpaRepository<TTipoIdentificacion, Long> {
	TTipoIdentificacion findByCodigo(String codigo);

	@Query("select t from TTipoIdentificacion t where not t.id =5 and not t.id =6 order by t.id ASC")
	List<TTipoIdentificacion> enlistarTipoIdentificacion();

	@Query(value = "SELECT t from TTipoIdentificacion t where t.codigo ='04' OR t.codigo = '05' OR t.codigo = '06'")
	public List<TTipoIdentificacion> obtenerTiposIdentificacionForProveedor();

	@Query(value = "SELECT t from TTipoIdentificacion t where t.codigo ='04' OR t.codigo = '05' OR t.codigo = '06' OR t.codigo = '07'")
	public List<TTipoIdentificacion> obtenerTiposIdentificacionFacturacion();
}
