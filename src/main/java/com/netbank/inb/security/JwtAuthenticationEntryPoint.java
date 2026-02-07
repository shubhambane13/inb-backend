package com.netbank.inb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netbank.inb.dto.ApiResponseMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        ApiResponseMessage build = ApiResponseMessage.builder().message(authException.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).success(false).build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), build);
    }
}
