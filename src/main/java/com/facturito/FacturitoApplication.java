package com.facturito;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FacturitoApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
		SpringApplication.run(FacturitoApplication.class, args);
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	@Bean
	public CommandLineRunner createPasswordsCommand(){
		return args -> {
//			System.out.println(new Date());
//			System.out.println(passwordEncoder.encode("admin"));
//			System.out.println(passwordEncoder.encode("admin"));
		};
	}

}
