package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final RepositoryUsuarios repositoryUsuarios;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RepositoryUsuarios repositoryUsuarios,
                       PasswordEncoder passwordEncoder) {
        this.repositoryUsuarios = repositoryUsuarios;
        this.passwordEncoder = passwordEncoder;
    }


    // Encontrar um usuário pelo e-mail
    public Optional<Usuarios> findByEmail(String email) {
        return repositoryUsuarios.findByEmail(email);
    }

    // Criar ou atualizar um usuário
    public Usuarios saveUser(Usuarios usuario) {
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repositoryUsuarios.save(usuario);
    }


    // Excluir usuário
    public void deleteUser(Long id) {
        repositoryUsuarios.deleteById(id);
    }

    // Outros métodos úteis, como listar, atualizar, etc.
    public Iterable<Usuarios> findAll() {
        return repositoryUsuarios.findAll();
    }

    public Optional<Usuarios> findById(Long id) {
        return repositoryUsuarios.findById(id);
    }

    // Por exemplo, um método para verificar se o usuário existe ao fazer login
    public boolean existsByEmail(String email) {
        return repositoryUsuarios.findByEmail(email).isPresent();
    }

}