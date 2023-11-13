package com.arqui.integrador.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arqui.integrador.security.JwtTokenManager;
import com.arqui.integrador.service.AuthService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JwtFilter.class);
	
	private static final String AUTH_URI = "/auth";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TOKEN = "Bearer ";
	
	@Autowired
	private AuthService authService;

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		String tokenHeader = request.getHeader(AUTHORIZATION_HEADER);
		String username = null;
		String token = null;
		if (requestUri.contains(AUTH_URI)) {
			if (tokenHeader != null && tokenHeader.startsWith(BEARER_TOKEN)) {
				token = tokenHeader.substring(tokenHeader.indexOf(" ") + 1);
				try {
					username = jwtTokenManager.getUsernameFromToken(token);
				} catch (IllegalArgumentException e) {
					LOG.error("Unable to get JWT Token", e);
				} catch (ExpiredJwtException e) {
					LOG.error("JWT Token has expired", e);
				}
			} else {
				LOG.info("Invalid authorization header");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = authService.loadUserByUsername(username);
			
			if (jwtTokenManager.validateJwtToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
