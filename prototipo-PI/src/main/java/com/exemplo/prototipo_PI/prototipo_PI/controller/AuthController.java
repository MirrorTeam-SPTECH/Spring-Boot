package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.CustomUserDetailsService;
import com.exemplo.prototipo_PI.prototipo_PI.Util.JwtUtil;
import com.exemplo.prototipo_PI.prototipo_PI.exception.UserAlreadyExistsException;
import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios loginRequest) {
        try {
            // troque authenticate(...) por authenticateManualWithExpiration(...)
            Map<String, Object> authData = authService.authenticateManualWithExpiration(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );
            return ResponseEntity.ok(authData);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciais inválidas"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuário não autenticado"));
        }

        String email = authentication.getName(); // Pega o e-mail do usuário logado (vindo do token)

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuarios usuario) {
        try {
            Usuarios usuarioRegistrado = authService.register(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("id", usuarioRegistrado.getId());
            response.put("email", usuarioRegistrado.getEmail());
            response.put("nome", usuarioRegistrado.getNome());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao registrar o usuário.");
            errorResponse.put("status", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
