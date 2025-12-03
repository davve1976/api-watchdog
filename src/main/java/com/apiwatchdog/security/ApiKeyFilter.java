package com.apiwatchdog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${apiwatchdog.api-key.enabled:true}")
    private boolean apiKeyEnabled;

    @Value("${apiwatchdog.api-key.value:changeme}")
    private String apiKeyValue;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Tillåt root, swagger, actuator och "public" API öppet
        if (path.equals("/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator")
                || path.startsWith("/api/public/")) {
            return true;
        }

        // Filtrera bara /api/**, resten (t.ex. static) släpps
        return !path.startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Om skydd avstängt → släpp igenom allt
        if (!apiKeyEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("X-API-Key");
        String trimmed = header == null ? null : header.trim();

        if (apiKeyValue != null && apiKeyValue.equals(trimmed)) {
            // Rätt nyckel → vidare till controller
            filterChain.doFilter(request, response);
        } else {
            // Fel eller saknad nyckel → 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("Invalid or missing API key");
        }
    }
}
