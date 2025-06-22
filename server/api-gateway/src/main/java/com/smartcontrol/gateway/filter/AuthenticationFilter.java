package com.smartcontrol.gateway.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcontrol.gateway.dto.GeneralResponse;
import com.smartcontrol.gateway.util.JwtProvider;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final String[] PUBLIC_ENDPOINTS = { "/auth/.*" };

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (this.isPublicEndpoint(exchange)) {
            return chain.filter(exchange);
        }

        List<String> token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (!this.checkToken(token)) {
            return this.unauthenticatedResponse(exchange.getResponse());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean checkToken(List<String> token) {
        if (CollectionUtils.isEmpty(token)) {
            return false;
        }

        String accessToken = token.get(0);
        if (!accessToken.startsWith("Bearer")) {
            return false;
        }

        return this.jwtProvider.isValidToken(accessToken.substring(7));
    }

    private boolean isPublicEndpoint(ServerWebExchange exchange) {
        return Arrays.stream(this.PUBLIC_ENDPOINTS)
                .anyMatch(endpoint -> exchange.getRequest().getURI().getPath().matches(endpoint));
    }

    private Mono<Void> unauthenticatedResponse(ServerHttpResponse response) {
        GeneralResponse apiResponse = GeneralResponse.builder().message("Unauthenticated").build();

        String body = null;
        try {
            body = new ObjectMapper().writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));

    }
}