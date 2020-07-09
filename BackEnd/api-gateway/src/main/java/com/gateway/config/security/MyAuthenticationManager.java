package com.gateway.config.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class MyAuthenticationManager implements ReactiveAuthenticationManager {
	
	@Autowired
	private JwtProvider jwtProvider;

	@SuppressWarnings("unchecked")
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		String username;
		
		try {
			username = jwtProvider.getUsernameFromJwtToken(authToken);
		} catch (Exception e) {
			e.printStackTrace();
			username = null;
		}
		
		if (username != null && !jwtProvider.isExpired(authToken)) {
			Claims claims = jwtProvider.getClaims(authToken);
			List<String> roles = claims.get(JwtProvider.AUTHORITY_KEY, List.class);
			List<SimpleGrantedAuthority> authorities = roles
					.stream()
					.map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					username, authToken, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
			return Mono.just(auth);
		}
		
		return Mono.empty();
	}

}
