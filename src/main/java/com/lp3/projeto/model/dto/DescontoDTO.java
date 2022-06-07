package com.lp3.projeto.model.dto;

import com.lp3.projeto.model.entity.Desconto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescontoDTO {

    private Long id;
    private String descricao;
    private Date dataInicio;
    private Date dataVencimento;
    private Long idCategoria;

    public static DescontoDTO create(Desconto desconto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(desconto, DescontoDTO.class);

    }

}
