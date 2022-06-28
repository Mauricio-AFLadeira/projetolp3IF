package com.lp3.projeto.api.controller;


import com.lp3.projeto.api.dto.ItemCompraDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.ItemCompra;
import com.lp3.projeto.service.ItemCompraService;
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
public class ItemCompraController {

    private final ItemCompraService service;

    @GetMapping()
    public ResponseEntity get(){
        List<ItemCompra> itensCompras = service.getItemCompra();
        return ResponseEntity.ok(itensCompras.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if(!itemCompra.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ItemCompraDTO dto) {
        try {
            ItemCompra itemPedido = converter(dto);
            itemPedido= service.salvar(itemPedido);
            return new ResponseEntity(itemPedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, ItemCompraDTO dto) {
        if (!service.getItemCompraById(id).isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemCompra itemCompra = converter(dto);
            itemCompra.setId(id);
            service.salvar(itemCompra);
            return ResponseEntity.ok(itemCompra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
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
        return modelMapper.map(dto, ItemCompra.class);
    }
}
