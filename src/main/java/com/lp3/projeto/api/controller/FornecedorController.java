package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.FormaPagamentoDTO;
import com.lp3.projeto.api.dto.FornecedorDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.service.EnderecoService;
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
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get(){
        List<Fornecedor> fornecedores= service.getFornecedor();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = service.getFornecdorById(id);
        if(!fornecedor.isPresent()){
            return new ResponseEntity("Fornecedor não encontrda", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(FornecedorDTO dto) {
        try {
            Fornecedor fornecedor = converter(dto);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            fornecedor = service.salvar(fornecedor);
            return new ResponseEntity(fornecedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, FornecedorDTO dto) {
        if (!service.getFornecdorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor.setId(id);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            service.salvar(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecdorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        fornecedor.setEndereco(endereco);
        return fornecedor;
    }

}
