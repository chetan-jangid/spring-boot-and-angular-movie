package com.gateway.config.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gateway.utils.AppUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JwtProvider {
	
	public static String AUTHORITY_KEY = "AUTHORITY_KEY";
//	private static String jwtSecret = "$#1H!#8";
	private static int jwtExpiration = 86400;
	
	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
	
	public String generateJwtToken(String username, List<String> authorities) {
		return Jwts.builder()
				.setSubject(username)
				.claim(AUTHORITY_KEY, authorities)
				.setIssuedAt(AppUtils.currentDate())
				.setExpiration(new Date((AppUtils.currentDate()).getTime() + jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, base64SecretBytes)
				.compact();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getUsernameFromJwtToken(String token) {
		return getClaims(token).getSubject();
	}
	
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(base64SecretBytes)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean isExpired(String token) {
		final Date expiration = getClaims(token).getExpiration();
		return expiration.before(AppUtils.currentDate());
	}

}
