package com.exemplo.prototipo_PI.prototipo_PI.model;

import java.util.List;

public class DashboardStats {
    private Long totalPedidos;
    private Double faturamentoTotal;
    private Double ticketMedio;
    private Long clientesAtivos;
    private List<ProdutoRanking> produtosRanking;

    public DashboardStats() {}

    public DashboardStats(Long totalPedidos, Double faturamentoTotal, Double ticketMedio, Long clientesAtivos, List<ProdutoRanking> produtosRanking) {
        this.totalPedidos = totalPedidos;
        this.faturamentoTotal = faturamentoTotal;
        this.ticketMedio = ticketMedio;
        this.clientesAtivos = clientesAtivos;
        this.produtosRanking = produtosRanking;
    }

    // Getters e Setters
    public Long getTotalPedidos() {
        return totalPedidos;
    }
    public void setTotalPedidos(Long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public Double getFaturamentoTotal() {
        return faturamentoTotal;
    }
    public void setFaturamentoTotal(Double faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    public Double getTicketMedio() {
        return ticketMedio;
    }
    public void setTicketMedio(Double ticketMedio) {
        this.ticketMedio = ticketMedio;
    }

    public Long getClientesAtivos() {
        return clientesAtivos;
    }
    public void setClientesAtivos(Long clientesAtivos) {
        this.clientesAtivos = clientesAtivos;
    }

    public List<ProdutoRanking> getProdutosRanking() {
        return produtosRanking;
    }
    public void setProdutosRanking(List<ProdutoRanking> produtosRanking) {
        this.produtosRanking = produtosRanking;
    }

    public static class ProdutoRanking {
        private String nomeProduto;
        private Long quantidade;
        private Double valorTotal;

        public ProdutoRanking() {}

        public ProdutoRanking(String nomeProduto, Long quantidade, Double valorTotal) {
            this.nomeProduto = nomeProduto;
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
        }

        public String getNomeProduto() {
            return nomeProduto;
        }
        public void setNomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
        }

        public Long getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(Long quantidade) {
            this.quantidade = quantidade;
        }

        public Double getValorTotal() {
            return valorTotal;
        }
        public void setValorTotal(Double valorTotal) {
            this.valorTotal = valorTotal;
        }
    }
}
