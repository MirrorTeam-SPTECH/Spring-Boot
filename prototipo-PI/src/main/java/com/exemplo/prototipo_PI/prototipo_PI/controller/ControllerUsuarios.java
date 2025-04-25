package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import com.exemplo.prototipo_PI.prototipo_PI.repository.RepositoryUsuarios;
import org.springframework.web.bind.annotation.*;
import com.exemplo.prototipo_PI.prototipo_PI.model.Usuarios;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {
    RepositoryUsuarios repositoryUsuario;

    public ControllerUsuarios(RepositoryUsuarios repositoryUsuario) {
        this.repositoryUsuario = repositoryUsuario;
    }

    @GetMapping()
    public List<Usuarios> listar(){
        return repositoryUsuario.findAll();
    }

    @PostMapping()
    public Usuarios adicionar(@RequestBody Usuarios usuario){
        return repositoryUsuario.save(usuario);
    }

    @DeleteMapping("/{id}")
    public String deletarUsuario(@PathVariable Long id){
        repositoryUsuario.deleteById(id);
        return "Usuario removido com sucesso";
    }

    @PutMapping("/{id}")
    public Usuarios atualizarCliente(@PathVariable Long id ,@RequestBody Usuarios novoUsuario){
        return repositoryUsuario.findById(id).map(usuario -> {
            usuario.setNome(novoUsuario.getNome());
            usuario.setEmail(novoUsuario.getEmail());
            usuario.setSenha(novoUsuario.getSenha());
            return repositoryUsuario.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @GetMapping("/{id}")
    public Optional<Usuarios> listarPorId(@PathVariable Long id){
        return repositoryUsuario.findById(id);

    }
}

