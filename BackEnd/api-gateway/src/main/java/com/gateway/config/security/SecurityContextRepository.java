package com.gateway.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
	
	private static final String TOKEN_PREFIX = "Bearer ";
	
	private List<String> tokens = new ArrayList<>();
	
	@Autowired
	private MyAuthenticationManager authenticationManager;
	
	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		String token = null;
		
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			token = header.replace(TOKEN_PREFIX, "");
		}
		
		if (token != null && tokens.contains(token)) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
			return authenticationManager
					.authenticate(authentication)
					.map(auth -> new SecurityContextImpl(auth));
		}
		
		return Mono.empty();
	}
	
	public void addToContext(String token) {
		tokens.add(token);
	}
	
	public void removeFromContext(String token) {
		tokens.remove(token);
	}

}
