package com.example.jwtvalidation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jwtvalidation.service.JwtService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Development/testing endpoint: creates a token for this experiment.
    @PostMapping("/generate")
    public Map<String, String> generateToken(
            @RequestParam(defaultValue = "manvith") String username,
            @RequestParam(defaultValue = "ROLE_USER") String role,
            @RequestParam(defaultValue = "false") boolean expired) {

        String token = jwtService.generateToken(username, List.of(role), expired);
        return Map.of("token", token);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            if (token == null || token.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token is required"));
            }

            Claims claims = jwtService.validateToken(token);
            Object roles = claims.get("roles");

            return ResponseEntity.ok(Map.of(
                    "message", "Token Valid",
                    "username", claims.getSubject(),
                    "roles", roles == null ? "" : roles
            ));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid Token"));
        }
    }
}
