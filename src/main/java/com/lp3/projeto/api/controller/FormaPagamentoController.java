package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.FormaPagamentoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.FormaPagamento;
import com.lp3.projeto.service.FormaPagamentoService;
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
@RequestMapping("/api/v1/formaPagamentos")
@RequiredArgsConstructor
@Api("API da Forma de Pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService service;

    @GetMapping()
    @ApiOperation("Obter todas as formas de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Formas de pagamentos obtidas"),
            @ApiResponse(code = 404, message = "Nenhuma forma de pagamento existente")
    })
    public ResponseEntity get() {
        List<FormaPagamento> formaPagamentos = service.getFormaDePagamento();
        return ResponseEntity.ok(formaPagamentos.stream().map(FormaPagamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento encontrada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da forma de pagamento") Long id){
        Optional<FormaPagamento> formaPagamento = service.getFormaDePagamentoById(id);
        if(!formaPagamento.isPresent()){
            return new ResponseEntity("Forma de pagamento não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(formaPagamento.map(FormaPagamentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adicionar uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento salva"),
            @ApiResponse(code = 400, message = "Erro ao salvar forma de pagamento")
    })
    public ResponseEntity post(FormaPagamentoDTO dto) {
        try {
            FormaPagamento formaPagamento = converter(dto);
            formaPagamento = service.salvar(formaPagamento);
            return new ResponseEntity(formaPagamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar credenciais de uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento alterada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id da Forma de pagamento") Long id, FormaPagamentoDTO dto) {
        if (!service.getFormaDePagamentoById(id).isPresent()) {
            return new ResponseEntity("Forma de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FormaPagamento formaPagamento = converter(dto);
            formaPagamento.setId(id);
            service.salvar(formaPagamento);
            return ResponseEntity.ok(formaPagamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da forma de pagamento") Long id) {
        Optional<FormaPagamento> formapagamento = service.getFormaDePagamentoById(id);
        if (!formapagamento.isPresent()) {
            return new ResponseEntity("Forma de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(formapagamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FormaPagamento converter(FormaPagamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, FormaPagamento.class);
    }
}
