package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.ItemPedidoService;
import com.exemplo.prototipo_PI.prototipo_PI.model.ItemPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/itens-pedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService service;

    @PostMapping
    public ResponseEntity<ItemPedido> post(@RequestBody ItemPedido itemPedido) throws IOException {
        service.inserirItem(itemPedido);
        return ResponseEntity.status(201).body(itemPedido);
    }

    @PatchMapping("/imagem/{id}")
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody byte[] conteudoImagem) throws IOException {
        service.atualizarImagem(id, conteudoImagem);
        return ResponseEntity.status(200).build();
    }
}
