package com.exemplo.prototipo_PI.prototipo_PI.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequest {
    private Double valor;
    private String descricao;
    private String email;
}
