package com.exemplo.prototipo_PI.prototipo_PI.repository;

import com.exemplo.prototipo_PI.prototipo_PI.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositoryPedidos extends JpaRepository<Pedido, Long> {

    @Query("SELECT COUNT(p) FROM Pedido p")
    Long countTotalPedidos();

    @Query("SELECT COALESCE(SUM(p.valor), 0.0) FROM Pedido p")
    Double sumFaturamentoTotal();

    @Query("SELECT COALESCE(AVG(p.valor), 0.0) FROM Pedido p")
    Double avgTicketMedio();

    @Query("SELECT COUNT(DISTINCT p.usuario.id) FROM Pedido p WHERE p.usuario IS NOT NULL")
    Long countClientesAtivos();

    @Query("SELECT p.nomeProduto, SUM(p.quantidade) as quantidade, SUM(p.valor) as valorTotal " +
            "FROM Pedido p WHERE p.nomeProduto IS NOT NULL " +
            "GROUP BY p.nomeProduto ORDER BY quantidade DESC")
    List<Object[]> findProdutosRanking();

    List<Pedido> findByUsuario_Id(Long usuarioId);

    List<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.dataCriacao >= :inicio AND p.dataCriacao <= :fim")
    Long countPedidosPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(p.valor), 0.0) FROM Pedido p WHERE p.dataCriacao >= :inicio AND p.dataCriacao <= :fim")
    Double sumFaturamentoPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
