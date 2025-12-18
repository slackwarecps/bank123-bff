package br.com.fabioalvaro.bank123.bffbank.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class MdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String correlationId = req.getHeader("x-correlation-id");
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put("x-correlation-id", correlationId);
        MDC.put("path", req.getRequestURI()); // Adiciona o Path da URL
        MDC.put("method", req.getMethod());   // Adiciona o método (GET, POST)

        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = req.getHeader(headerName);

            if ("Authorization".equalsIgnoreCase(headerName) || "x-itau-auth".equalsIgnoreCase(headerName)) {
                headerValue = "?";
            }

            headers.append(headerName).append(": ").append(headerValue);
            if (headerNames.hasMoreElements()) {
                headers.append(" | ");
            }
        }
        log.info("Nova request recebida: {} {} Headers: [{}]", req.getMethod(), req.getRequestURI(), headers);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("x-correlation-id");
            MDC.remove("path");
            MDC.remove("method");
        }
    }
}