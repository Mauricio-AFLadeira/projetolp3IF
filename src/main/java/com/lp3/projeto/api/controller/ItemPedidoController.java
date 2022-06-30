package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.ItemPedidoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.ItemPedido;
import com.lp3.projeto.model.entity.Pedido;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.service.ItemPedidoService;
import com.lp3.projeto.service.PedidoService;
import com.lp3.projeto.service.ProdutoService;
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
public class ItemPedidoController {

    private final ItemPedidoService service;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    @GetMapping()
    public ResponseEntity get(){
        List<ItemPedido> itensPedidos = service.getItemPedido();
        return ResponseEntity.ok(itensPedidos.stream().map(ItemPedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<ItemPedido> itemPedido = service.getItemPedidoById(id);
        if(!itemPedido.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(itemPedido.map(ItemPedidoDTO::create));
    }

    @PostMapping()
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
    public ResponseEntity put(@PathVariable("id") Long id, ItemPedidoDTO dto) {
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
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
