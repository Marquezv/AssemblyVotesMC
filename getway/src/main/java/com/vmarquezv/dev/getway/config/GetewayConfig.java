package com.vmarquezv.dev.getway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetewayConfig {
	

	
		@Bean
		public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
			return builder.routes()
					.route("user", r -> r
							.path("/users/**")
							.filters(f -> f.rewritePath("/(?<id>.*)", "/${id}"))
							.uri("http://172.22.0.4:8081"))
					.route("surveys", r -> r
							.path("/surveys/**")
							.filters(f -> f.rewritePath("/(?<id>.*)", "/${id}"))
							.uri("http://172.22.0.4:8081"))
					
					.route("sessions", r -> r
							.path("/sessions/**")
							.filters(f -> f.rewritePath("/(?<id>.*)", "/${id}"))
							.uri("http://172.22.0.5:8082"))
					.route("votes", r -> r
							.path("/votes/**")
							.filters(f -> f.rewritePath("/(?<id>.*)", "/${id}"))
							.uri("http://172.22.0.5:8082"))
					.build();
		}
}
