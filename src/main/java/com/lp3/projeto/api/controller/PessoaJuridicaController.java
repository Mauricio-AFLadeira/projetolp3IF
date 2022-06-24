package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.PessoaJuridicaDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.service.EnderecoService;
import com.lp3.projeto.service.PessoaJuridicaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pessoasJuridicas")
@RequiredArgsConstructor
public class PessoaJuridicaController {

    private final PessoaJuridicaService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get(){
        List<PessoaJuridica> pessoaJuridicas = service.getPessoaJuridica();
        return ResponseEntity.ok(pessoaJuridicas.stream().map(PessoaJuridicaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<PessoaJuridica> pessoaJuridica = service.getPessoaJuridicaById(id);
        if (!pessoaJuridica.isPresent()){
            return new ResponseEntity("Pessoa Jurídica não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pessoaJuridica.map(PessoaJuridicaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(PessoaJuridicaDTO dto) {
        try {
            PessoaJuridica pessoaJuridica = converter(dto);
            Endereco endereco = enderecoService.salvar(pessoaJuridica.getEndereco());
            pessoaJuridica.setEndereco(endereco);
            pessoaJuridica = service.salvar(pessoaJuridica);
            return new ResponseEntity(pessoaJuridica, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, PessoaJuridicaDTO dto) {
        if (!service.getPessoaJuridicaById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PessoaJuridica pessoaJuridica = converter(dto);
            pessoaJuridica.setId(id);
            Endereco endereco = enderecoService.salvar(pessoaJuridica.getEndereco());
            pessoaJuridica.setEndereco(endereco);
            service.salvar(pessoaJuridica);
            return ResponseEntity.ok(pessoaJuridica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<PessoaJuridica> pessoaJuridica = service.getPessoaJuridicaById(id);
        if (!pessoaJuridica.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pessoaJuridica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PessoaJuridica converter(PessoaJuridicaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PessoaJuridica pessoaJuridica = modelMapper.map(dto, PessoaJuridica.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        pessoaJuridica.setEndereco(endereco);
        return pessoaJuridica;
    }
}
