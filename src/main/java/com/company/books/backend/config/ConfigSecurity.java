package com.company.books.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ConfigSecurity {
	
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		
		UserDetails miguel = User.builder()
				.username("miguel")
				.password("{noop}miguel123")
				.roles("Empleado")
				.build();
		
		UserDetails agustin= User.builder()
				.username("agustin")
				.password("{noop}agustin123")
				.roles("Empleado","Jefe")
				.build();
		
		UserDetails edita = User.builder()
				.username("edita")
				.password("{noop}edita123")
				.roles("Empleado","Jefe")
				.build();
		
		return new InMemoryUserDetailsManager(miguel, agustin, edita);
		
	}
}
