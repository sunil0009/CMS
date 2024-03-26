package com.example.cms.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private CustomUserDetailService UserDetailService;

	public SecurityConfig(CustomUserDetailService userDetailService) {
		super();
		UserDetailService = userDetailService;
	}
	//===================================================
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(UserDetailService);
		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder(12);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// http is a security paramater
		//csrf- cross sight request forging disabling it here 
		return http.csrf(csrf-> csrf.disable())
				        .authorizeHttpRequests(auth->auth.requestMatchers("/users/register")
						.permitAll().anyRequest().authenticated())
				        .formLogin(Customizer.withDefaults()).build();

	}
}
