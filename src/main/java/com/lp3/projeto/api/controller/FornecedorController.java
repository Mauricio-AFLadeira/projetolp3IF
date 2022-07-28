package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.FornecedorDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Endereco;
import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.service.EnderecoService;
import com.lp3.projeto.service.FornecedorService;
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
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
@Api("API de Fornecedores")
public class FornecedorController {

    private final FornecedorService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    @ApiOperation("Obter todos os fornecedores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedores obtidos"),
            @ApiResponse(code = 404, message = "Nenhum fornecedor existente")
    })
    public ResponseEntity get(){
        List<Fornecedor> fornecedores= service.getFornecedor();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um fornecedor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Fornecedor") Long id){
        Optional<Fornecedor> fornecedor = service.getFornecdorById(id);
        if(!fornecedor.isPresent()){
            return new ResponseEntity("Fornecedor não encontrda", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar um fornecedor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Fornecedor salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar desconto")
    })
    public ResponseEntity post(FornecedorDTO dto) {
        try {
            Fornecedor fornecedor = converter(dto);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            fornecedor = service.salvar(fornecedor);
            return new ResponseEntity(fornecedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de um fornecedor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor alterado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Fornecedor") Long id, FornecedorDTO dto) {
        if (!service.getFornecdorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor.setId(id);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            service.salvar(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir fornecedor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Fornecedor excluído"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da Fornecedor") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecdorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        fornecedor.setEndereco(endereco);
        return fornecedor;
    }

}
