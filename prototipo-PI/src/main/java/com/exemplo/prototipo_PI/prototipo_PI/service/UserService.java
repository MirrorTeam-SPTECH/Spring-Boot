package com.exemplo.prototipo_PI.prototipo_PI.service;

import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios; // Ajuste para o modelo Usuarios
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios; // Ajuste para o repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RepositoryUsuarios repositoryUsuarios; // Usando seu repositório

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para autenticar o usuário
    public boolean authenticate(String email, String senha) {
        Optional<Usuarios> usuario = repositoryUsuarios.findByEmail(email);
        if (usuario.isPresent()) {
            String senhaArmazenada = usuario.get().getSenha();
            boolean matches = passwordEncoder.matches(senha, senhaArmazenada);
            System.out.println("E-mail: " + email + ", Senha fornecida: " + senha + ", Senha armazenada: " + senhaArmazenada + ", Matches: " + matches);
            return matches;
        }

        System.out.println("Usuário não encontrado: " + email);
        return false;
    }

    // Método para criar um novo usuário (opcional, caso precise)
    public Usuarios registrarUsuario(Usuarios novoUsuario) {
        novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha())); // Codifica a senha
        return repositoryUsuarios.save(novoUsuario); // Salva o usuário no banco
    }
}