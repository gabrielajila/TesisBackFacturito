package com.facturito.DAO;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturito.entity.TFormaPagoComprobantes;


public interface TFormaPagoComprobantesDAO extends JpaRepository<TFormaPagoComprobantes, Long> {

	@Query("SELECT SUM(fp.valor) FROM TFormaPagoComprobantes fp where fp.factura!=null AND fp.tipoPago = 'Efectivo' AND fp.factura.idCliente.idCliente= :idCliente")
	BigDecimal buscarFacturasEfectivo(Long idCliente);

	@Query("SELECT SUM(fp.valor) FROM TFormaPagoComprobantes fp where fp.factura!=null AND UPPER(fp.tipoPago) = 'CREDITO' AND fp.factura.idCliente.idCliente= :idCliente")
	BigDecimal buscarFacturasConCreditoActivo(Long idCliente);

}
