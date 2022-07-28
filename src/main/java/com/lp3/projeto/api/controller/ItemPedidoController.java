package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.ItemPedidoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.ItemPedido;
import com.lp3.projeto.model.entity.Pedido;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.service.ItemPedidoService;
import com.lp3.projeto.service.PedidoService;
import com.lp3.projeto.service.ProdutoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/itensPedidos")
@RequiredArgsConstructor
@Api("API de Item Pedido")
public class ItemPedidoController {

    private final ItemPedidoService service;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    @GetMapping()
    @ApiOperation("Obter todos os Itens Pedidos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Itens pedidos obtidos"),
            @ApiResponse(code = 404, message = "Nenhum item pedido existente")
    })
    public ResponseEntity get(){
        List<ItemPedido> itensPedidos = service.getItemPedido();
        return ResponseEntity.ok(itensPedidos.stream().map(ItemPedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um item pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item pedido encontrado"),
            @ApiResponse(code = 404, message = "Item pedido não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<ItemPedido> itemPedido = service.getItemPedidoById(id);
        if(!itemPedido.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(itemPedido.map(ItemPedidoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar um item pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item pedido salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar o item pedido")
    })
    public ResponseEntity post(ItemPedidoDTO dto) {
        try {
            ItemPedido itemPedido = converter(dto);
            Produto produto = produtoService.salvar(itemPedido.getProduto());
            itemPedido.setProduto(produto);
            Pedido pedido = pedidoService.salvar(itemPedido.getPedido());
            itemPedido.setPedido(pedido);
            itemPedido= service.salvar(itemPedido);
            return new ResponseEntity(itemPedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de um item pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item pedido alterado"),
            @ApiResponse(code = 404, message = "Item pedido não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Item Pedido") Long id, ItemPedidoDTO dto) {
        if (!service.getItemPedidoById(id).isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemPedido itemPedido = converter(dto);
            itemPedido.setId(id);
            Produto produto = produtoService.salvar(itemPedido.getProduto());
            itemPedido.setProduto(produto);
            Pedido pedido = pedidoService.salvar(itemPedido.getPedido());
            itemPedido.setPedido(pedido);
            service.salvar(itemPedido);
            return ResponseEntity.ok(itemPedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir Item Pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Item pedido excluído"),
            @ApiResponse(code = 404, message = "Item pedido não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Item Pedido") Long id) {
        Optional<ItemPedido> itemPedido = service.getItemPedidoById(id);
        if (!itemPedido.isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemPedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ItemPedido converter(ItemPedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ItemPedido itemPedido = modelMapper.map(dto, ItemPedido.class);
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                itemPedido.setProduto(null);
            } else {
                itemPedido.setProduto(produto.get());
            }
        }
        if (dto.getIdPedido() != null) {
            Optional<Pedido> pedido = pedidoService.getPedidoById(dto.getIdPedido());
            if (!pedido.isPresent()) {
                itemPedido.setPedido(null);
            } else {
                itemPedido.setPedido(pedido.get());
            }
        }
        return itemPedido;
    }
}
