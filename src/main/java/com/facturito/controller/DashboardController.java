package com.facturito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturito.model.RespSimple;
import com.facturito.service.DashboardService;

@RestController
@RequestMapping("dashboard/")
@CrossOrigin("*")
public class DashboardController {

	@Autowired private DashboardService dashboard;
	
	@PostMapping("getFacturasByMonth")
	public  ResponseEntity<RespSimple> getFacturasByMonth(@RequestParam Long idCliente){
		return dashboard.getFacturasMeses(idCliente);
	}
	
}
