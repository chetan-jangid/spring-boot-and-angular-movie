package com.gateway.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	private MyAuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	@Bean
	public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
		UserDetails admin = User.withUsername("ADMIN")
		.password("$2a$10$Z21.LpBETR/doNIoU2.lLuCrF8LrA.wwkgYoQjTnDad8WLPsu8Mo6")
		.roles("ADMIN")
		.build();
		
		return new MapReactiveUserDetailsService(admin);
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
		.cors()
		.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
		.and()
		.csrf()
		.disable()
		.exceptionHandling()
		.authenticationEntryPoint((serverWebExchange, e) -> Mono.fromRunnable(() -> {
			serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		}))
		.accessDeniedHandler((serverWebExchange, e) -> Mono.fromRunnable(() -> {
			serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		}))
		.and()
		.authenticationManager(authenticationManager)
		.securityContextRepository(securityContextRepository)
		.authorizeExchange()
		.pathMatchers(
				"/movies/movie/save", "/movies/movie/update", "/movies/movie/delete",
				"/multiplex/manage/save", "/multiplex/manage/update", "/multiplex/manage/delete"
				)
		.hasRole("ADMIN")
		.pathMatchers("/**")
		.permitAll()
		.and()
		.httpBasic();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
