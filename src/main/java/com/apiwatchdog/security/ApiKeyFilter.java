package com.apiwatchdog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final boolean API_KEY_ENABLED = true;
    private static final String API_KEY_VALUE = "changeme";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Tillåt root + swagger + actuator helt öppet
        return path.equals("/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator")
                || !path.startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!API_KEY_ENABLED) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("X-API-Key");
        String trimmed = header == null ? null : header.trim();

        System.out.println("X-API-Key raw   = '" + header + "'");
        System.out.println("X-API-Key trim  = '" + trimmed + "'");
        System.out.println("Expected value  = '" + API_KEY_VALUE + "'");
        System.out.println("Equals?         = " + API_KEY_VALUE.equals(trimmed));

        if (API_KEY_VALUE.equals(trimmed)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
