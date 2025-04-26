package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.exception.UserAlreadyExistsException;
import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import com.exemplo.prototipo_PI.prototipo_PI.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;           // ← injetei o UserService aqui

    @Autowired
    private RepositoryUsuarios repositoryUsuarios;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Autenticação manual, puxando direto do repositório
    public String authenticateManual(String email, String senha) {
        Usuarios usuario = repositoryUsuarios.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        // Compara a senha crua com o hash salvo
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        // Se chegou até aqui, gera e retorna o token
        return jwtUtil.generateToken(email);
    }

    // Se quiser expiração junto:


    // Registra novo usuário
    public Usuarios register(Usuarios usuario) {
        if (userService.existsByEmail(usuario.getEmail())) {
            throw new UserAlreadyExistsException("Usuário já existe: " + usuario.getEmail());
        }
        // criptografa e salva no banco
        return userService.saveUser(usuario);
    }

    // Método opcional de login com expiração no map
    public Map<String, Object> authenticateManualWithExpiration(String email, String senha) {
        String token = authenticateManual(email, senha);
        Date expiresAt = jwtUtil.extractExpiration(token);
        return Map.of("token", token, "expiresAt", expiresAt);
    }
}