package com.exemplo.prototipo_PI.prototipo_PI.adapter;

public class PagamentoAdapter {
    private final PagamentoExterno pagamentoExterno;

    public PagamentoAdapter() {
        this.pagamentoExterno = new PagamentoExterno();
    }

    public void pagar(double valor) {
        pagamentoExterno.realizarPagamentoExterno(valor);
    }
}
