package com.lp3.projeto.api.controller;


import com.lp3.projeto.api.dto.PessoaFisicaDTO;
import com.lp3.projeto.model.entity.PessoaFisica;
import com.lp3.projeto.service.PessoaFisicaService;
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
@RequestMapping("/api/v1/pessoasFisicas")
@RequiredArgsConstructor
public class PessoaFisicaController {

    private final PessoaFisicaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<PessoaFisica> pessoaFisicas = service.getPessoaFisica();
        return ResponseEntity.ok(pessoaFisicas.stream().map(PessoaFisicaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<PessoaFisica> pessoaFisica = service.getPessoaFisicaById(id);
        if (!pessoaFisica.isPresent()){
            return new ResponseEntity("Pessoa Física não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(pessoaFisica.map(PessoaFisicaDTO::create));
    }
}
