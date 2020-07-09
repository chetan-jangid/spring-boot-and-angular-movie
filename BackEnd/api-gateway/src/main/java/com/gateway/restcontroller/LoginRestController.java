package com.gateway.restcontroller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.config.security.JwtProvider;
import com.gateway.config.security.SecurityContextRepository;
import com.gateway.dto.LoginDto;
import com.gateway.dto.LoginResponseDto;
import com.gateway.exception.BadCredentialsException;

@RestController
@RequestMapping("/api-gateway")
public class LoginRestController {
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) 
			throws BadCredentialsException {
		Authentication auth = null;
		String token = null;
		if (isAdmin(loginDto)) {
			token = jwtProvider.generateJwtToken(loginDto.getUsername(), Arrays.asList("ROLE_ADMIN"));
			auth = new UsernamePasswordAuthenticationToken(
					loginDto.getUsername(), token, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
			SecurityContextHolder.getContext().setAuthentication(auth);
			securityContextRepository.addToContext(token);
		}
		
		if (auth == null) {
			throw new BadCredentialsException("Invalid Credentials");
		}
		
		return new ResponseEntity<LoginResponseDto>(new LoginResponseDto(token), HttpStatus.OK);
	}
	
	@PostMapping("/user-logout")
	public ResponseEntity<Void> logout() {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	private boolean isAdmin(LoginDto loginDto) {
		return loginDto.getUsername().equals("admin") && loginDto.getPassword().equals("12345");
	}

}
