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
import java.util.List; // 🔥 IMPORT NECESSÁRIO

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

    public ListaItemPedidos buscarTodos() throws IOException {
        var arquivo = new File("%s/%s/%s".formatted(caminhoFe, caminhoJson, arquivoJson));
        return mapper.readValue(arquivo, ListaItemPedidos.class);
    }

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

    public void atualizarItem(ItemPedido itemPedido) throws IOException {
        var arquivo = new File("%s/%s/%s".formatted(caminhoFe, caminhoJson, arquivoJson));

        ListaItemPedidos listaItemPedidos = mapper.readValue(arquivo, ListaItemPedidos.class);

        // Encontrar e atualizar o item na categoria correta
        boolean itemAtualizado = false;

        switch (itemPedido.getCategoria()) {
            case "combos" -> {
                for (int i = 0; i < listaItemPedidos.getCombos().size(); i++) {
                    if (listaItemPedidos.getCombos().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getCombos().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
            case "bebidas" -> {
                for (int i = 0; i < listaItemPedidos.getBebidas().size(); i++) {
                    if (listaItemPedidos.getBebidas().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getBebidas().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
            case "hamburgeres" -> {
                for (int i = 0; i < listaItemPedidos.getHamburgueres().size(); i++) {
                    if (listaItemPedidos.getHamburgueres().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getHamburgueres().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
            case "espetinhos" -> {
                for (int i = 0; i < listaItemPedidos.getEspetinhos().size(); i++) {
                    if (listaItemPedidos.getEspetinhos().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getEspetinhos().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
            case "porcoes" -> {
                for (int i = 0; i < listaItemPedidos.getPorcoes().size(); i++) {
                    if (listaItemPedidos.getPorcoes().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getPorcoes().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
            case "adicionais" -> {
                for (int i = 0; i < listaItemPedidos.getAdicionais().size(); i++) {
                    if (listaItemPedidos.getAdicionais().get(i).getId() == itemPedido.getId()) {
                        listaItemPedidos.getAdicionais().set(i, itemPedido);
                        itemAtualizado = true;
                        break;
                    }
                }
            }
        }

        if (!itemAtualizado) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Item não encontrado");
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, listaItemPedidos);
    }

    // 🔥 MÉTODO DELETE COMPLETO
    public void deletarItem(int id) throws IOException {
        var arquivo = new File("%s/%s/%s".formatted(caminhoFe, caminhoJson, arquivoJson));

        ListaItemPedidos listaItemPedidos = mapper.readValue(arquivo, ListaItemPedidos.class);

        // Variável para rastrear se o item foi encontrado e removido
        boolean itemRemovido = false;

        // Procurar e remover o item da categoria correta
        if (removerItemDaLista(listaItemPedidos.getCombos(), id)) {
            itemRemovido = true;
        } else if (removerItemDaLista(listaItemPedidos.getBebidas(), id)) {
            itemRemovido = true;
        } else if (removerItemDaLista(listaItemPedidos.getHamburgueres(), id)) {
            itemRemovido = true;
        } else if (removerItemDaLista(listaItemPedidos.getEspetinhos(), id)) {
            itemRemovido = true;
        } else if (removerItemDaLista(listaItemPedidos.getPorcoes(), id)) {
            itemRemovido = true;
        } else if (removerItemDaLista(listaItemPedidos.getAdicionais(), id)) {
            itemRemovido = true;
        }

        if (!itemRemovido) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Item não encontrado");
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, listaItemPedidos);
    }

    // 🔥 MÉTODO AUXILIAR PARA REMOVER ITEM
    private boolean removerItemDaLista(List<ItemPedido> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                return true;
            }
        }
        return false;
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
