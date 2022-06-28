package com.lp3.projeto.api.dto;

import com.lp3.projeto.model.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;
    private String nome;
    private Long idCategoriaPai;

    public static CategoriaDTO create(Categoria categoria){
        //ModelMapper modelMapper = new ModelMapper();
        //return modelMapper.map(categoria, CategoriaDTO.class);
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setIdCategoriaPai(categoria.getCategoriaPai().getId());

        return  dto;
    }
}
