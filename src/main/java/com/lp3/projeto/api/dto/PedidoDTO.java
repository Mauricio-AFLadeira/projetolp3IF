package com.lp3.projeto.api.dto;

import com.lp3.projeto.model.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private String dataDoPedido;
    private Long idPessoa;

    public static PedidoDTO create(Pedido pedido){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
