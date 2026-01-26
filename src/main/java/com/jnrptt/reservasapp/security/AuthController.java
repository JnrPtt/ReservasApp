package com.jnrptt.reservasapp.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = jwtService.generarToken(request.username());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    public record LoginRequest(String username) {}

    public record TokenResponse(String token) {}
}
