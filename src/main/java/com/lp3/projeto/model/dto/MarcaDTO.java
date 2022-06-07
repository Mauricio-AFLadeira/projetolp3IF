package com.lp3.projeto.model.dto;

import com.lp3.projeto.model.entity.Marca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaDTO {

    private Long id;
    private String nome;

    public static MarcaDTO create(Marca marca){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(marca, MarcaDTO.class);
    }
}
