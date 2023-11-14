package com.arqui.integrador.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.net.HttpHeaders;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private WebClient.Builder webClientBuilder;
	
	public AuthenticationFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		LOG.info("gateway filter");
		return ((exchange, chain)->{
			LOG.info("inside return");
			if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new RuntimeException("Missin auth header");
			}
			
			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			
			String[] parts = authHeader.split(" ");
			
			if(parts.length != 2 || !parts[0].equals("Bearer")) {
				throw new RuntimeException("Incorrect auth structure");
			}
			
			return webClientBuilder.build()
					.post()
					.uri("http://localhost:8080/auth/validate?token=" + parts[1])
					.retrieve().bodyToMono(String.class).map( string ->{
						exchange.getRequest()
						.mutate()
						.header("", parts);
						return exchange;
					}).flatMap(chain::filter);

		});
	}

	
	public static class Config {
		
	}
}
