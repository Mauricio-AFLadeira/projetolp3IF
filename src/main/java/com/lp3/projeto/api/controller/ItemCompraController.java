package com.lp3.projeto.api.controller;


import com.lp3.projeto.api.dto.ItemCompraDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Compra;
import com.lp3.projeto.model.entity.ItemCompra;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.service.CompraService;
import com.lp3.projeto.service.ItemCompraService;
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
@RequestMapping("/api/v1/itensCompras")
@RequiredArgsConstructor
@Api("API de Itens Compras")
public class ItemCompraController {

    private final ItemCompraService service;
    private final CompraService compraService;
    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Obter todos os itens compras")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Itens compras obtidos"),
            @ApiResponse(code = 404, message = "Nenhum item compra existente")
    })
    public ResponseEntity get(){
        List<ItemCompra> itensCompras = service.getItemCompra();
        return ResponseEntity.ok(itensCompras.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um item compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item compra encontrado"),
            @ApiResponse(code = 404, message = "Item compra não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do item compra") Long id){
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if(!itemCompra.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar um item compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item compra salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar item compra")
    })
    public ResponseEntity post(ItemCompraDTO dto) {
        try {
            ItemCompra itemCompra = converter(dto);
            Produto produto = produtoService.salvar(itemCompra.getProduto());
            itemCompra.setProduto(produto);
            Compra compra = compraService.salvar(itemCompra.getCompra());
            itemCompra.setCompra(compra);
            itemCompra= service.salvar(itemCompra);
            return new ResponseEntity(itemCompra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de um item compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item compra alterado"),
            @ApiResponse(code = 404, message = "Item compra não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Item compra") Long id, ItemCompraDTO dto) {
        if (!service.getItemCompraById(id).isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemCompra itemCompra = converter(dto);
            itemCompra.setId(id);
            Produto produto = produtoService.salvar(itemCompra.getProduto());
            itemCompra.setProduto(produto);
            Compra compra = compraService.salvar(itemCompra.getCompra());
            itemCompra.setCompra(compra);
            service.salvar(itemCompra);
            return ResponseEntity.ok(itemCompra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir item compra")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Item compra excluído"),
            @ApiResponse(code = 404, message = "Item compra não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Item Compra") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemCompra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ItemCompra converter(ItemCompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ItemCompra itemCompra = modelMapper.map(dto, ItemCompra.class);
        if (dto.getIdCompra() != null) {
            Optional<Compra> compra = compraService.getCompraById(dto.getIdCompra());
            if (!compra.isPresent()) {
                itemCompra.setCompra(null);
            } else {
                itemCompra.setCompra(compra.get());
            }
        }
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                itemCompra.setProduto(null);
            } else {
                itemCompra.setProduto(produto.get());
            }
        }
        return itemCompra;
    }
}
