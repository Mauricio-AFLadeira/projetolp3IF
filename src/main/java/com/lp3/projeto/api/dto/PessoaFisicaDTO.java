package com.lp3.projeto.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaDTO {

    private Long id;
    private String login;
    private String senha;
    private String telefone;
    private String email;
    private String nome;
    private String cpf;
    private String dataDeNascimento;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
