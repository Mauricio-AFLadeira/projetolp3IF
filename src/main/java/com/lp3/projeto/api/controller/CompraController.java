package com.lp3.projeto.api.controller;



import com.lp3.projeto.api.dto.CompraDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Compra;
import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.service.CompraService;
import com.lp3.projeto.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController {

        private final CompraService service;
        private  final FornecedorService fornecedorService;

        @GetMapping()
        public ResponseEntity get(){
            List<Compra> compras = service.getCompra();
            return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
        }

        @GetMapping("/{id}")
        public ResponseEntity get(@PathVariable("id") Long id){
            Optional<Compra> compra = service.getCompraById(id);
            if(!compra.isPresent()){
                return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(compra.map(CompraDTO::create));
        }

        @PostMapping()
        public ResponseEntity post(CompraDTO dto) {
            try {
                Compra compra = converter(dto);
                Fornecedor fornecedor = fornecedorService.salvar(compra.getFornecedor());
                compra.setFornecedor(fornecedor);
                compra = service.salvar(compra);
                return new ResponseEntity(compra, HttpStatus.CREATED);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }



    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, CompraDTO dto) {
        if (!service.getCompraById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Compra compra = converter(dto);
            compra.setId(id);
            Fornecedor fornecedor = fornecedorService.salvar(compra.getFornecedor());
            compra.setFornecedor(fornecedor);
            service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        if (dto.getIdFornecedor() != null) {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecdorById(dto.getIdFornecedor());
            if (!fornecedor.isPresent()) {
                compra.setFornecedor(null);
            } else {
                compra.setFornecedor(fornecedor.get());
            }
        }
        return compra;
    }
}
