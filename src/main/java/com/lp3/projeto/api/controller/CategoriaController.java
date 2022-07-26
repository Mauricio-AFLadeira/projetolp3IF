package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.CategoriaDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Categoria;
import com.lp3.projeto.service.CategoriaService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Api("API de Categoria")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping()
    @ApiOperation("Obter todas as categorias")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categorias obtidas"),
            @ApiResponse(code = 404, message = "Nenhuma categoria existente")
    })
    public ResponseEntity get(){
        List<Categoria> categorias = service.getCategoria();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da Categoria") Long id){
        Optional<Categoria> categoria= service.getCategoriaById(id);
        if(!categoria.isPresent()){
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria salva"),
            @ApiResponse(code = 400, message = "Erro ao salvar categoria")
    })
    public ResponseEntity post(CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            categoria = service.salvar(categoria);
            return new ResponseEntity(categoria, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria alterada"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id da categoria") Long id, CategoriaDTO dto) {
        if (!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            service.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Categoria excluída"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da categoria") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Categoria converter(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());

        if (dto.getIdCategoriaPai() != null) {
            Optional<Categoria> cat = service.getCategoriaById(dto.getIdCategoriaPai());
            if (!cat.isPresent())
                categoria.setCategoriaPai(null);
            else {
                categoria.setCategoriaPai(cat.get());
            }
        }

        //ModelMapper modelMapper = new ModelMapper();
        //return modelMapper.map(dto, Categoria.class);
        return categoria;
    }
}
