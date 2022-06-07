package com.lp3.projeto.model.dto;

import com.lp3.projeto.model.entity.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoDTO {

    private Long id;
    private String nome;
    private String descricao;

    public static FormaPagamentoDTO create(FormaPagamento formaPagamento){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }
}
