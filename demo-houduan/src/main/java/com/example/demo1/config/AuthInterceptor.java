package com.example.demo1.config;

import com.example.demo1.response.ResponseCode;
import com.example.demo1.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_HEADER = "pdmtoken";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || token.isBlank()) {
            writeUnauthorized(response, ResponseCode.NOT_LOGIN);
            return false;
        }

        if (!JwtUtil.isTokenValid(token)) {
            writeUnauthorized(response, ResponseCode.TOKEN_INVALID);
            return false;
        }

        request.setAttribute("userId", JwtUtil.parseToken(token).get("userId"));
        request.setAttribute("username", JwtUtil.parseToken(token).get("username"));
        return true;
    }

    private void writeUnauthorized(HttpServletResponse response, ResponseCode code) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> body = new HashMap<>();
        body.put("code", code.getCode());
        body.put("message", code.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
