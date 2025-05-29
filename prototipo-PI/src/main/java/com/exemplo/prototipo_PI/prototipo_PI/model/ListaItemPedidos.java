package com.exemplo.prototipo_PI.prototipo_PI.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListaItemPedidos {
    private List<ItemPedido> combos;
    private List<ItemPedido> hamburgueres;
    private List<ItemPedido> espetinhos;
    private List<ItemPedido> adicionais;
    private List<ItemPedido> bebidas;
    private List<ItemPedido> porcoes;
}
