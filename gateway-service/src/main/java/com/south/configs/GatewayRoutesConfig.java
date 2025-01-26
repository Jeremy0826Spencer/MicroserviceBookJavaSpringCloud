package com.south.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r.path("/books/**")
                        .uri("lb://book-service"))
                .route("inventory-service", r -> r.path("/inventory/**")
                        .uri("lb://inventory-service"))
                .route("location-service", r -> r.path("/branches/**")
                        .uri("lb://location-service"))
                .build();
    }
}
