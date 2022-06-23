package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.DescontoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Desconto;
import com.lp3.projeto.service.DescontoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/descontos")
@RequiredArgsConstructor
public class DescontoController {

    private final DescontoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Desconto> descontos = service.getDesconto();
        return ResponseEntity.ok(descontos.stream().map(DescontoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Desconto> desconto = service.getDescontoById(id);
        if(!desconto.isPresent()){
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(desconto.map(DescontoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(DescontoDTO dto) {
        try {
            Desconto desconto = converter(dto);
            desconto= service.salvar(desconto);
            return new ResponseEntity(desconto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, DescontoDTO dto) {
        if (!service.getDescontoById(id).isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Desconto desconto = converter(dto);
            desconto.setId(id);
            service.salvar(desconto);
            return ResponseEntity.ok(desconto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Desconto> desconto = service.getDescontoById(id);
        if (!desconto.isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(desconto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Desconto converter(DescontoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Desconto.class);
    }
}
