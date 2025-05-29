package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.model.ItemPedido;
import com.exemplo.prototipo_PI.prototipo_PI.model.ListaItemPedidos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ObjectMapper mapper;

    @Value("${caminho.fe}")
    private String caminhoFe;

    @Value("${caminho.imagens}")
    private String caminhoImagens;

    @Value("${caminho.json}")
    private String caminhoJson;

    @Value("${arquivo.json}")
    private String arquivoJson;


    public void inserirItem(ItemPedido itemPedido) throws IOException {

        var arquivo = new File("%s/%s/%s".formatted(caminhoFe, caminhoJson, arquivoJson));

        ListaItemPedidos listaItemPedidos = mapper.readValue(arquivo, ListaItemPedidos.class);

        var novoId = listaItemPedidos.getCombos().size()+listaItemPedidos.getBebidas().size()
                +listaItemPedidos.getHamburgueres().size()+listaItemPedidos.getEspetinhos().size()
                +listaItemPedidos.getPorcoes().size()+listaItemPedidos.getAdicionais().size()+1;

        itemPedido.setId(novoId);

        switch (itemPedido.getCategoria()) {
            case "combos" -> listaItemPedidos.getCombos().add(itemPedido);
            case "bebidas" -> listaItemPedidos.getBebidas().add(itemPedido);
            case "hamburgeres" -> listaItemPedidos.getHamburgueres().add(itemPedido);
            case "espetinhos" -> listaItemPedidos.getEspetinhos().add(itemPedido);
            case "porcoes" -> listaItemPedidos.getPorcoes().add(itemPedido);
            case "adicionais" -> listaItemPedidos.getAdicionais().add(itemPedido);
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, listaItemPedidos);
    }


    public void atualizarImagem(int id, byte[] conteudoImagem) throws IOException {

        var arquivo = new File("%s/%s/%s".formatted(caminhoFe, caminhoJson, arquivoJson));

        ListaItemPedidos listaItemPedidos = mapper.readValue(arquivo, ListaItemPedidos.class);
        ItemPedido itemPedido = listaItemPedidos.getBebidas().stream().filter(item -> item.getId() == id).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        String nomeArquivoImagem = "%s-%d.png".formatted(itemPedido.getNome(), id);
        String caminhoCompletoArquivoImagem = "%s%s/%s".formatted(caminhoFe, caminhoImagens, nomeArquivoImagem);
        try (FileOutputStream fos = new FileOutputStream(caminhoCompletoArquivoImagem)) {
            fos.write(conteudoImagem);
        }

        itemPedido.setImagem("/img/%s".formatted(nomeArquivoImagem));

        mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, listaItemPedidos);
    }

}
