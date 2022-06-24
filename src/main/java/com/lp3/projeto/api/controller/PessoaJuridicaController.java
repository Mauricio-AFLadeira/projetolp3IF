package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.PessoaFisicaDTO;
import com.lp3.projeto.api.dto.PessoaJuridicaDTO;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.entity.PessoaFisica;
import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.service.PessoaJuridicaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/v1/pessoasJuridicas")
@RequiredArgsConstructor
public class PessoaJuridicaController {

    private final PessoaJuridicaService service;

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

    public PessoaJuridica converter(PessoaJuridicaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PessoaJuridica pessoaJuridica = modelMapper.map(dto, PessoaJuridica.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        pessoaJuridica.setEndereco(endereco);
        return pessoaJuridica;
    }
}
