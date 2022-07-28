package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.DescontoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Categoria;
import com.lp3.projeto.model.entity.Desconto;
import com.lp3.projeto.service.CategoriaService;
import com.lp3.projeto.service.DescontoService;
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
@RequestMapping("/api/v1/descontos")
@RequiredArgsConstructor
@Api("API de Desconto")
public class DescontoController {

    private final DescontoService service;
    private final CategoriaService categoriaService;

    @GetMapping()
    @ApiOperation("Obter todos os descontos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Descontos obtidos"),
            @ApiResponse(code = 404, message = "Nenhum desconto existente")
    })
    public ResponseEntity get(){
        List<Desconto> descontos = service.getDesconto();
        return ResponseEntity.ok(descontos.stream().map(DescontoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um desconto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Desconto encontrado"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Desconto") Long id){
        Optional<Desconto> desconto = service.getDescontoById(id);
        if(!desconto.isPresent()){
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(desconto.map(DescontoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar um desconto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Desconto salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar desconto")
    })
    public ResponseEntity post(DescontoDTO dto) {
        try {
            Desconto desconto = converter(dto);
            Categoria categoria = categoriaService.salvar(desconto.getCategoria());
            desconto.setCategoria(categoria);
            desconto= service.salvar(desconto);
            return new ResponseEntity(desconto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de um desconto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Desconto alterado"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Desconto") Long id, DescontoDTO dto) {
        if (!service.getDescontoById(id).isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Desconto desconto = converter(dto);
            desconto.setId(id);
            Categoria categoria = categoriaService.salvar(desconto.getCategoria());
            desconto.setCategoria(categoria);
            service.salvar(desconto);
            return ResponseEntity.ok(desconto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir desconto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desconto excluído"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da Desconto") Long id) {
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
        Desconto desconto = modelMapper.map(dto, Desconto.class);
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!categoria.isPresent()) {
                desconto.setCategoria(null);
            } else {
                desconto.setCategoria(categoria.get());
            }
        }
        return desconto;
    }
}
