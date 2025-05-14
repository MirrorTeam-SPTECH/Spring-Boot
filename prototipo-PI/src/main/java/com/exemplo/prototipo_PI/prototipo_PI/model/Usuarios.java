package com.exemplo.prototipo_PI.prototipo_PI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios") // Nome da tabela no banco de dados
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id;

    @NotNull
    @Email // Validação para que o campo tenha um formato de e-mail válido
    @Column(unique = true) // Garante que não existam e-mails duplicados
    private String email;

    @NotNull
    private String senha; // Armazena a senha (deve ser criptografada)

    @NotBlank // Garante que o nome não seja nulo ou vazio
    private String nome; // Nome completo do usuário


}
