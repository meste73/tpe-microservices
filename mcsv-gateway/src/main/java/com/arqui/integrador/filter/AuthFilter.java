package com.arqui.integrador.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.arqui.integrador.security.JwtUtil;
import com.arqui.integrador.security.RouteValidator;
import com.google.common.net.HttpHeaders;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);
	
	private RouteValidator routeValidator;
	
	private JwtUtil jwtUtil;
	
	public AuthFilter(RouteValidator routeValidator, JwtUtil jwtUtil) {
		super(Config.class);
		this.routeValidator = routeValidator;
		this.jwtUtil =jwtUtil;
	}

	@Override
	public GatewayFilter apply(Config config) {
		LOG.info("gateway filter");
		return ((exchange, chain)->{
			LOG.info("inside return");
			
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				LOG.info("inside route validator if");
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Missin auth header");
				}
				
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				
				String[] parts = authHeader.split(" ");
				
				if(parts.length != 2 || !parts[0].equals("Bearer")) {
					throw new RuntimeException("Incorrect auth structure");
				}
				try {
					jwtUtil.validateJwtToken(parts[1]);
				}catch(Exception e) {
					throw new RuntimeException("Unauthorized access");
				}
			}
			return chain.filter(exchange);
		});
	}

	
	public static class Config {
		
	}
}
