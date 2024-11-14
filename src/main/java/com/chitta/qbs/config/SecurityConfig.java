package com.chitta.qbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chitta.qbs.config.security.util.JwtAuthFilter;
import com.chitta.qbs.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/authenticate").permitAll()
				.requestMatchers("/api/customers/**").hasRole("USER").anyRequest().authenticated().and()
				.exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response
						.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
