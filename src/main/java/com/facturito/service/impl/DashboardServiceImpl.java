package com.facturito.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facturito.DAO.TFacturaDAO;
import com.facturito.consultas.DashboardFacturaAnioMes;
import com.facturito.consultas.DashboardFacturasDTO;
import com.facturito.model.RespSimple;
import com.facturito.service.DashboardService;
import com.facturito.util.FacturitoEnum;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private TFacturaDAO facturaDAO;

	@Override
	public ResponseEntity<RespSimple> getFacturasMeses(Long idCliente) {
		try {
			Pageable pageable = PageRequest.of(0, 6);
			LocalDate fechaHaceSeisMeses = LocalDate.now().minusMonths(6);

			Date fechaHaceSeisMesesDate = Date
					.from(fechaHaceSeisMeses.atStartOfDay(ZoneId.systemDefault()).toInstant());
			List<DashboardFacturaAnioMes> listaFacturas = facturaDAO.getFacturasLast6Mont(idCliente,
					fechaHaceSeisMesesDate, pageable);
			List<DashboardFacturasDTO> facturasDTO = new ArrayList<>();
			for (DashboardFacturaAnioMes d : listaFacturas) {
				facturasDTO.add(new DashboardFacturasDTO(d.getAnio(), d.getMes(), d.getCantidad()));
			}
			RespSimple response = new RespSimple();
			response.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			response.setMensaje("");
			response.setData(completarMesesConCero(facturasDTO));
			return new ResponseEntity<RespSimple>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("error: " + e);
			return new ResponseEntity<RespSimple>(new RespSimple(FacturitoEnum.TRANSACCION_ERROR.getId(), "", null),
					HttpStatus.OK);
		}
	}

    public static List<DashboardFacturasDTO> completarMesesConCero(List<DashboardFacturasDTO> datosFacturas) {
        List<DashboardFacturasDTO> ultimosSeisMeses = new ArrayList<>();

        // Crear registros para los últimos 6 meses
        LocalDate fecha = LocalDate.now().minusMonths(5);
        for (int i = 0; i < 6; i++) {
            ultimosSeisMeses.add(new DashboardFacturasDTO(fecha.getYear(), fecha.getMonthValue(), 0));
            fecha = fecha.plusMonths(1);
        }

        // Actualizar los registros con los datos existentes
        for (DashboardFacturasDTO registroExistente : datosFacturas) {
            ultimosSeisMeses.removeIf(registro -> registro.getAnio() == registroExistente.getAnio()
                    && registro.getMes() == registroExistente.getMes());
            ultimosSeisMeses.add(registroExistente);
        }

        // Ordenar la lista final
        Collections.sort(ultimosSeisMeses, new Comparator<DashboardFacturasDTO>() {
            @Override
            public int compare(DashboardFacturasDTO o1, DashboardFacturasDTO o2) {
                int yearCompare = Integer.compare(o1.getAnio(), o2.getAnio());
                if (yearCompare != 0) {
                    return yearCompare;
                }
                return Integer.compare(o1.getMes(), o2.getMes());
            }
        });

        return ultimosSeisMeses;
    }

}
