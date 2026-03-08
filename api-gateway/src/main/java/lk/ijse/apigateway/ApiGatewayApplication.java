package lk.ijse.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("crop-inventory-service", r -> r
                        .path("/api/crops", "/api/crops/**")
                        .uri("lb://CROP-INVENTORY-SERVICE"))
                .route("zone-management-service", r -> r
                        .path("/api/zones", "/api/zones/**")
                        .uri("lb://ZONE-MANAGEMENT-SERVICE"))
                .build();
    }
}