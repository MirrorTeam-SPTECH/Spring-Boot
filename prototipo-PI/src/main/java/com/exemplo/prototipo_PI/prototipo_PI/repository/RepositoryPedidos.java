package com.exemplo.prototipo_PI.prototipo_PI.repository;

import com.exemplo.prototipo_PI.prototipo_PI.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPedidos extends JpaRepository<Pedido, Long> {
}
