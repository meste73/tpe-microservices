package com.arqui.integrador.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.security.JwtUtil;
import com.arqui.integrador.security.RouteValidator;
import com.google.common.net.HttpHeaders;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain)->{
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Missin auth header");
				}
			}
			
			String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			if(authHeaders != null && authHeaders.startsWith("Bearer ")) {
				authHeaders=authHeaders.substring(7);
			}
			
			try {
				//restTemplate.getForObject("http://localhost:8080/auth/validate?token"+authHeaders, String.class);
				jwtUtil.validateJwtToken(authHeaders);
			}catch(Exception e) {
				throw new RuntimeException("unauthorized access to application, rest template");
			}
			return chain.filter(exchange);
		});
	}
	
	public static class Config {
		
	}
}
