package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.adapter.PagamentoAdapter;
import com.exemplo.prototipo_PI.prototipo_PI.model.Pedido;
import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryPedidos;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class ControllerPedidos {

    private final RepositoryPedidos repositoryPedidos;
    private final RepositoryUsuarios repositoryUsuarios;
    private final PagamentoAdapter pagamentoAdapter;

    public ControllerPedidos(RepositoryPedidos repositoryPedidos, RepositoryUsuarios repositoryUsuarios) {
        this.repositoryPedidos = repositoryPedidos;
        this.repositoryUsuarios = repositoryUsuarios;
        this.pagamentoAdapter = new PagamentoAdapter();
    }

    @PostMapping("/{idUsuario}")
    public Pedido fazerPedido(@PathVariable Long idUsuario, @RequestBody Pedido pedido) {
        Usuarios usuario = repositoryUsuarios.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        pedido.setUsuario(usuario);

        pagamentoAdapter.pagar(pedido.getValor()); // Aqui aplicamos o padrão Adapter

        return repositoryPedidos.save(pedido);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return repositoryPedidos.findAll();
    }
}
