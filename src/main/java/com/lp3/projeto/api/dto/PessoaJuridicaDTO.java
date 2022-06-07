package com.lp3.projeto.api.dto;

import com.lp3.projeto.model.entity.PessoaJuridica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridicaDTO {

    private Long id;
    private String login;
    private String senha;
    private String telefone;
    private String email;
    private String nome;
    private String cnpj;
    private String responsavel;
    private String empresa;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static PessoaJuridicaDTO create(PessoaJuridica pessoaJuridica){
        ModelMapper modelMapper = new ModelMapper();
        PessoaJuridicaDTO dto = modelMapper.map(pessoaJuridica, PessoaJuridicaDTO.class);
        dto.logradouro= pessoaJuridica.getEndereco().getLogradouro();
        dto.numero= pessoaJuridica.getEndereco().getNumero();
        dto.complemento= pessoaJuridica.getEndereco().getComplemento();
        dto.bairro= pessoaJuridica.getEndereco().getBairro();
        dto.cidade= pessoaJuridica.getEndereco().getCidade();
        dto.uf= pessoaJuridica.getEndereco().getUf();
        dto.cep= pessoaJuridica.getEndereco().getCep();
        return dto;
    }
}
