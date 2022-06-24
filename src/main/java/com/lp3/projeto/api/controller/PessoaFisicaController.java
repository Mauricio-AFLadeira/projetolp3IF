package com.lp3.projeto.api.controller;


import com.lp3.projeto.api.dto.PessoaFisicaDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.entity.PessoaFisica;
import com.lp3.projeto.service.EnderecoService;
import com.lp3.projeto.service.PessoaFisicaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pessoasFisicas")
@RequiredArgsConstructor
public class PessoaFisicaController {

    private final PessoaFisicaService service;
    private final EnderecoService enderecoService;

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

    @PostMapping()
    public ResponseEntity post(PessoaFisicaDTO dto) {
        try {
            PessoaFisica pessoaFisica = converter(dto);
            Endereco endereco = enderecoService.salvar(pessoaFisica.getEndereco());
            pessoaFisica.setEndereco(endereco);
            pessoaFisica = service.salvar(pessoaFisica);
            return new ResponseEntity(pessoaFisica, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, PessoaFisicaDTO dto) {
        if (!service.getPessoaFisicaById(id).isPresent()) {
            return new ResponseEntity("Pessoa Fisica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PessoaFisica pessoaFisica = converter(dto);
            pessoaFisica.setId(id);
            Endereco endereco = enderecoService.salvar(pessoaFisica.getEndereco());
            pessoaFisica.setEndereco(endereco);
            service.salvar(pessoaFisica);
            return ResponseEntity.ok(pessoaFisica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<PessoaFisica> pessoaFisica = service.getPessoaFisicaById(id);
        if (!pessoaFisica.isPresent()) {
            return new ResponseEntity("Pessoa Fisica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pessoaFisica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PessoaFisica converter(PessoaFisicaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PessoaFisica pessoaFisica = modelMapper.map(dto, PessoaFisica.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        pessoaFisica.setEndereco(endereco);
        return pessoaFisica;
    }
}
