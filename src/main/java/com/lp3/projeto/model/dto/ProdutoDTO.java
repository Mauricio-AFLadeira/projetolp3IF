package com.lp3.projeto.model.dto;

import com.lp3.projeto.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Integer valorUnitario;
    private String imagem;
    private String estoqueMax;
    private String estoqueMin;
    private String estoqueRessuprimento;
    private String qtdeEstoque;
    private Long idCategoria;
    private Long idMarca;

    public static ProdutoDTO create(Produto produto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}
