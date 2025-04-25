package com.exemplo.prototipo_PI.prototipo_PI.repository;

import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUsuarios extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByEmail(String email); // Método para buscar usuário por email
}
