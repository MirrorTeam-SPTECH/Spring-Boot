package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.model.DashboardStats;
import com.exemplo.prototipo_PI.prototipo_PI.model.ItemAdicional;
import com.exemplo.prototipo_PI.prototipo_PI.model.Pedido;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private RepositoryPedidos repositoryPedidos;

    public Pedido salvarPedido(Pedido pedido) {
        // Calcular valor total se não estiver definido
        if (pedido.getValor() == null && pedido.getValorUnitario() != null && pedido.getQuantidade() != null) {
            Double valorItens = pedido.getValorUnitario() * pedido.getQuantidade();

            // Trocar para BigDecimal
            BigDecimal valorAdicionais = BigDecimal.ZERO;

            if (pedido.getAdicionais() != null) {
                for (ItemAdicional adicional : pedido.getAdicionais()) {
                    BigDecimal subtotal = adicional.getPrecoUnitario()
                            .multiply(BigDecimal.valueOf(adicional.getQuantidade()));
                    valorAdicionais = valorAdicionais.add(subtotal);
                }
            }

            // Se quiser somar com os itens, transforma tudo pra BigDecimal:
            BigDecimal valorTotal = BigDecimal.valueOf(valorItens).add(valorAdicionais);
            pedido.setValor(valorTotal.doubleValue()); // ou setar como BigDecimal, depende do tipo da sua variável
        }

        return repositoryPedidos.save(pedido);
    }

    public List<Pedido> buscarTodosPedidos() {
        return repositoryPedidos.findAll();
    }

    public List<Pedido> buscarPedidosPorUsuario(Long usuarioId) {
        return repositoryPedidos.findByUsuario_Id(usuarioId);
    }

    public DashboardStats obterEstatisticasDashboard() {
        DashboardStats stats = new DashboardStats();

        // Total de pedidos
        Long totalPedidos = repositoryPedidos.countTotalPedidos();
        stats.setTotalPedidos(totalPedidos != null ? totalPedidos : 0L);

        // Faturamento total
        Double faturamento = repositoryPedidos.sumFaturamentoTotal();
        stats.setFaturamentoTotal(faturamento != null ? faturamento : 0.0);

        // Ticket médio
        Double ticketMedio = repositoryPedidos.avgTicketMedio();
        stats.setTicketMedio(ticketMedio != null ? ticketMedio : 0.0);

        // Clientes ativos
        Long clientesAtivos = repositoryPedidos.countClientesAtivos();
        stats.setClientesAtivos(clientesAtivos != null ? clientesAtivos : 0L);

        // Ranking de produtos
        List<Object[]> rankingData = repositoryPedidos.findProdutosRanking();
        List<DashboardStats.ProdutoRanking> ranking = new ArrayList<>();

        for (Object[] row : rankingData) {
            String nomeProduto = (String) row[0];
            Long quantidade = ((Number) row[1]).longValue();
            Double valorTotal = ((Number) row[2]).doubleValue();

            ranking.add(new DashboardStats.ProdutoRanking(nomeProduto, quantidade, valorTotal));
        }

        stats.setProdutosRanking(ranking);

        return stats;
    }

    public List<Pedido> buscarPedidosPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repositoryPedidos.findByDataCriacaoBetween(inicio, fim);
    }

    public DashboardStats obterEstatisticasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        DashboardStats stats = new DashboardStats();

        // Total de pedidos no período
        Long totalPedidos = repositoryPedidos.countPedidosPorPeriodo(inicio, fim);
        stats.setTotalPedidos(totalPedidos != null ? totalPedidos : 0L);

        // Faturamento no período
        Double faturamento = repositoryPedidos.sumFaturamentoPorPeriodo(inicio, fim);
        stats.setFaturamentoTotal(faturamento != null ? faturamento : 0.0);

        // Ticket médio no período
        if (totalPedidos != null && totalPedidos > 0 && faturamento != null) {
            stats.setTicketMedio(faturamento / totalPedidos);
        } else {
            stats.setTicketMedio(0.0);
        }

        // Para clientes ativos e ranking, usar dados gerais por enquanto
        Long clientesAtivos = repositoryPedidos.countClientesAtivos();
        stats.setClientesAtivos(clientesAtivos != null ? clientesAtivos : 0L);

        List<Object[]> rankingData = repositoryPedidos.findProdutosRanking();
        List<DashboardStats.ProdutoRanking> ranking = new ArrayList<>();

        for (Object[] row : rankingData) {
            String nomeProduto = (String) row[0];
            Long quantidade = ((Number) row[1]).longValue();
            Double valorTotal = ((Number) row[2]).doubleValue();

            ranking.add(new DashboardStats.ProdutoRanking(nomeProduto, quantidade, valorTotal));
        }

        stats.setProdutosRanking(ranking);

        return stats;
    }
}
