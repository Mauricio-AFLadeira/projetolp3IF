package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.FornecedorDTO;
import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

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

}
