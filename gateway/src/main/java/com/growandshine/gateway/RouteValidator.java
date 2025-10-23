package com.growandshine.gateway;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openEndPoints = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/validate",
            "/eureka"
    );

    // Predicate to check if the route is secured
    public boolean isSecured(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        // Return true if path is not in open routes
        return openEndPoints.stream().noneMatch(path::matches);
    }
}
