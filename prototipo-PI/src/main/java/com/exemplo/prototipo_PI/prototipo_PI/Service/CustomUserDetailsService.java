package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositoryUsuarios repositoryUsuarios;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = repositoryUsuarios.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                new ArrayList<>() // Aqui você pode adicionar as authorities/roles do usuário se necessário
        );
    }

}