package com.facturito.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.facturito.DAO.TClienteDAO;
import com.facturito.entity.TCliente;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TClienteDAO userRepository;

	@Autowired
	private JwtService jwtService;

	public AuthenticationResponse login(AuthenticationRequest authRequest) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());

		authenticationManager.authenticate(authToken);

		TCliente user = userRepository.getCliente(authRequest.getUsername());

		String jwt = jwtService.generateToken(user, generateExtraClaims(user));

		return new AuthenticationResponse(jwt);
	}

	private Map<String, Object> generateExtraClaims(TCliente user) {

		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("name", user.getNombres() + " " + user.getApellidos());
		extraClaims.put("role", user.getRole().name());
		extraClaims.put("permissions", user.getAuthorities());
		return extraClaims;
	}
}