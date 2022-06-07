package com.lp3.projeto.api.dto;


import com.lp3.projeto.model.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Long id;
    private String dataDaCompra;
    private Long idFornecedor;

    public static CompraDTO create(Compra compra){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(compra, CompraDTO.class);
    }
}
