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

    // Support both property names for backwards compatibility
    @Value("${apiwatchdog.api-key.value:}")
    private String apiKeyValuePrimary;

    @Value("${apiwatchdog.security.api-key:}")
    private String apiKeyValueLegacy;

    private String getEffectiveApiKey() {
        if (apiKeyValuePrimary != null && !apiKeyValuePrimary.isBlank()) {
            return apiKeyValuePrimary.trim();
        }
        if (apiKeyValueLegacy != null && !apiKeyValueLegacy.isBlank()) {
            return apiKeyValueLegacy.trim();
        }
        return null;
    }

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

        // accept common header casings
        String header = request.getHeader("X-API-Key");
        if (header == null) header = request.getHeader("X-API-KEY");
        if (header == null) header = request.getHeader("x-api-key");
        String trimmed = header == null ? null : header.trim();

        String effective = getEffectiveApiKey();
        if (effective != null && effective.equals(trimmed)) {
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