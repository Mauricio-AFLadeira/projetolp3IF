package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.MarcaDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Marca> marcas=service.getMarcas();
        return ResponseEntity.ok(marcas.stream().map(MarcaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public  ResponseEntity get(@PathVariable("id") Long id){
        Optional<Marca> marca = service.getMarcaById(id);
        if (!marca.isPresent()){
            return new ResponseEntity("Marca não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(marca.map(MarcaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(MarcaDTO dto) {
        try {
            Marca marca = converter(dto);
            marca = service.salvar(marca);
            return new ResponseEntity(marca, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, MarcaDTO dto) {
        if (!service.getMarcaById(id).isPresent()) {
            return new ResponseEntity("Marca não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Marca marca = converter(dto);
            marca.setId(id);
            service.salvar(marca);
            return ResponseEntity.ok(marca);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Marca> marca = service.getMarcaById(id);
        if (!marca.isPresent()) {
            return new ResponseEntity("Curso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(marca.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Marca converter(MarcaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Marca.class);
    }
}
