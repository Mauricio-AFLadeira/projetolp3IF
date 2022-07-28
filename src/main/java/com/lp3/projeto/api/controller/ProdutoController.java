package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.ProdutoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Categoria;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.model.entity.Produto;
import com.lp3.projeto.service.CategoriaService;
import com.lp3.projeto.service.MarcaService;
import com.lp3.projeto.service.ProdutoService;
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
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@Api("Api de Produto")
public class ProdutoController {

    private final ProdutoService service;
    private final MarcaService marcaService;
    private final CategoriaService categoriaService;

    @GetMapping()
    @ApiOperation("Obter todos os Produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produtos obtidos"),
            @ApiResponse(code = 404, message = "Nenhum produto existente")
    })
    public ResponseEntity get(){
        List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Produto") Long id){
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()){
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar produto")
    })
    public ResponseEntity post(ProdutoDTO dto) {
        try {
            Produto produto = converter(dto);
            Marca marca = marcaService.salvar(produto.getMarca());
            produto.setMarca(marca);
            Categoria categoria = categoriaService.salvar(produto.getCategoria());
            produto.setCategoria(categoria);
            produto = service.salvar(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto alterado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Produto") Long id, ProdutoDTO dto) {
        if (!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            Marca marca = marcaService.salvar(produto.getMarca());
            produto.setMarca(marca);
            Categoria categoria = categoriaService.salvar(produto.getCategoria());
            produto.setCategoria(categoria);
            service.salvar(produto);
            return ResponseEntity.ok(produto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    @ApiOperation("Excluir produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto excluído"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Produto") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        if (dto.getIdMarca() != null) {
            Optional<Marca> marca = marcaService.getMarcaById(dto.getIdMarca());
            if (!marca.isPresent()) {
                produto.setMarca(null);
            } else {
                produto.setMarca(marca.get());
            }
        }
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> cat = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!cat.isPresent())
                produto.setCategoria(null);
            else {
                produto.setCategoria(cat.get());
            }
        }
        //Marca marca = modelMapper.map(dto, Marca.class);
        //produto.setMarca(marca);
        //Categoria categoria = modelMapper.map(dto, Categoria.class);
        //produto.setCategoria(categoria);
        return produto;
    }
}
