package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.ItemPedidoService;
import com.exemplo.prototipo_PI.prototipo_PI.model.ItemPedido;
import com.exemplo.prototipo_PI.prototipo_PI.model.ListaItemPedidos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/itens-pedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService service;

    @GetMapping
    public ResponseEntity<ListaItemPedidos> getAll() throws IOException {
        ListaItemPedidos lista = service.buscarTodos(); // método que vamos criar em seguida
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> put(@PathVariable int id, @RequestBody ItemPedido itemPedido) throws IOException {
        itemPedido.setId(id); // Garantir que o ID está correto
        service.atualizarItem(itemPedido);
        return ResponseEntity.ok(itemPedido);
    }


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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws IOException {
        service.deletarItem(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
