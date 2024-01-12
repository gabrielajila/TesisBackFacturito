package com.facturito.filter;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*")
@Component
public class ClienteFilter implements Filter {

	Logger log = Logger.getLogger(ClienteFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			log.info("-> ".concat(httpRequest.getRequestURI().toString()));
		} catch (Exception e) {
			log.warning("ERROR AL MAPEAR LA RUTA SOLICITADA");
		}

		
			chain.doFilter(request, response);
		
	}


}