package com.netbank.inb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netbank.inb.dto.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error(String.valueOf(authException));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        ApiResponseMessage build = ApiResponseMessage.builder().message("message: " + authException.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).success(false).build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), build);
    }
}
