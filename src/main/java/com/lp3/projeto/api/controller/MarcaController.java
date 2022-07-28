package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.MarcaDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.service.MarcaService;
import io.swagger.annotations.*;
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
@Api("API de Marca")
public class MarcaController {

    private final MarcaService service;

    @GetMapping()
    @ApiOperation("Obter todos as marcas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Marcas obtidas"),
            @ApiResponse(code = 404, message = "Nenhum marca existente")
    })
    public ResponseEntity get(){
        List<Marca> marcas=service.getMarcas();
        return ResponseEntity.ok(marcas.stream().map(MarcaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma marca")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Marca encontrada"),
            @ApiResponse(code = 404, message = "Marca não encontrada")
    })
    public  ResponseEntity get(@PathVariable("id") @ApiParam("Id da Marca") Long id){
        Optional<Marca> marca = service.getMarcaById(id);
        if (!marca.isPresent()){
            return new ResponseEntity("Marca não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(marca.map(MarcaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar uma marca")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Marca salva"),
            @ApiResponse(code = 400, message = "Erro ao salvar marca")
    })
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
    @ApiOperation("Alterar credenciais de uma marca")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Marca alterada"),
            @ApiResponse(code = 404, message = "Marca não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id da Marca") Long id, MarcaDTO dto) {
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
    @ApiOperation("Excluir marca")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Marca excluída"),
            @ApiResponse(code = 404, message = "Marca não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da Marca") Long id) {
        Optional<Marca> marca = service.getMarcaById(id);
        if (!marca.isPresent()) {
            return new ResponseEntity("Marca não encontrado", HttpStatus.NOT_FOUND);
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
