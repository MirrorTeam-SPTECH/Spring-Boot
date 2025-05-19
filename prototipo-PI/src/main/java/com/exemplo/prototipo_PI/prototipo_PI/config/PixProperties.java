package com.exemplo.prototipo_PI.prototipo_PI.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "pix")
public class PixProperties {
    private String chave;
    private String nomeRecebedor;
    private String cidade;

}