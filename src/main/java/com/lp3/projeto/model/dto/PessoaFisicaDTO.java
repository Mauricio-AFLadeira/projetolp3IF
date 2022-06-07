package com.lp3.projeto.model.dto;

import com.lp3.projeto.model.entity.PessoaFisica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

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
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private  String cpf;
    private Date dataDeNascimento;

    public static PessoaFisicaDTO create(PessoaFisica pessoaFisica){
        ModelMapper modelMapper = new ModelMapper();
        PessoaFisicaDTO dto = modelMapper.map(pessoaFisica, PessoaFisicaDTO.class);
        dto.logradouro=pessoaFisica.getEndereco().getLogradouro();
        dto.numero=pessoaFisica.getEndereco().getNumero();
        dto.complemento=pessoaFisica.getEndereco().getComplemento();
        dto.bairro=pessoaFisica.getEndereco().getBairro();
        dto.cidade=pessoaFisica.getEndereco().getCidade();
        dto.uf=pessoaFisica.getEndereco().getUf();
        dto.cep=pessoaFisica.getEndereco().getCep();
        return dto;
    }
}
