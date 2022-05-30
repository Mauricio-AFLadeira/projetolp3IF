package com.lp3.projeto.api.dto;

import com.lp3.projeto.model.entity.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long id;
    private Integer qtdeProduto;
    private Long idPedido;
    private Long idProduto;

    public static ItemPedidoDTO create(ItemPedido itemPedido){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(itemPedido, ItemPedidoDTO.class);
    }
}
