package com.facturito.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturito.entity.TCodigosIce;


public interface TCodigosIceDAO extends JpaRepository<TCodigosIce, Long>{

	TCodigosIce findByCodigoIce(Long codigo);
}
