package com.exemplo.prototipo_PI.prototipo_PI.model;


import com.fasterxml.jackson.annotation.JsonProperty;



public class ItemPedido {
    private int id;
    private String nome;
    private String tempoPreparo;
    private String preco;
    private String imagem;
    private String descricao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String categoria;

    public ItemPedido() {}

    // Construtor
    public ItemPedido(int id, String nome, String tempoPreparo, String preco, String imagem, String descricao, String categoria) {
        this.id = id;
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.preco = preco;
        this.imagem = imagem;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // Getters e Setters


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}