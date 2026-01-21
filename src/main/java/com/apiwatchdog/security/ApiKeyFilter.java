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

    private String getEffectiveApiKey() {
        if (apiKeyValuePrimary != null && !apiKeyValuePrimary.isBlank()) {
            return apiKeyValuePrimary.trim();
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Endast /api/check är skyddat av API-nyckel
        if (path.startsWith("/api/check")) {
            return false;
        }

        // Tillåt övriga API:er
        return true;
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