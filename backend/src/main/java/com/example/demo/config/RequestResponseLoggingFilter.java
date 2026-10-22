package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

// ReubenVF: This filter records every inbound request and outbound response for troubleshooting and audit purposes.
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    // ReubenVF: Capture request and response payloads once per HTTP call and log the details after processing.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            String requestBody = readBody(wrappedRequest.getContentAsByteArray());
            String responseBody = readBody(wrappedResponse.getContentAsByteArray());
            String headers = Collections.list(request.getHeaderNames())
                    .stream()
                    .map(name -> name + "=" + request.getHeader(name))
                    .collect(Collectors.joining(", "));

            log.info("REQUEST {} {} headers=[{}] body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    headers,
                    requestBody);

            log.info("RESPONSE {} {} status={} elapsedMs={} body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    System.currentTimeMillis() - startTime,
                    responseBody);

            wrappedResponse.copyBodyToResponse();
        }
    }

    // ReubenVF: Converts raw bytes into readable text so request and response payloads are easy to inspect in logs.
    private String readBody(byte[] content) {
        if (content == null || content.length == 0) {
            return "empty";
        }
        return new String(content, StandardCharsets.UTF_8);
    }
}
