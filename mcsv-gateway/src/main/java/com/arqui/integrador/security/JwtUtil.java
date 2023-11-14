package com.arqui.integrador.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);
	
	public boolean validateJwtToken(String token) {
		LOG.info("validate token");
		Claims claims = Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token)
				.getBody();

		boolean isTokenExpired = claims.getExpiration().before(new Date());

		return (!isTokenExpired);
	}
}
