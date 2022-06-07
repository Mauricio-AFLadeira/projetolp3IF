package com.lp3.projeto.api.controller;

import com.lp3.projeto.model.dto.FormaPagamentoDTO;
import com.lp3.projeto.model.entity.FormaPagamento;
import com.lp3.projeto.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/formaPagamentos")
@RequiredArgsConstructor

public class FormaPagamentoController {

    private final FormaPagamentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<FormaPagamento> formaPagamentos = service.getFormaDePagamento();
        return ResponseEntity.ok(formaPagamentos.stream().map(FormaPagamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<FormaPagamento> formaPagamento = service.getFormaDePagamentoById(id);
        if(!formaPagamento.isPresent()){
            return new ResponseEntity("Forma de pagamento não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(formaPagamento.map(FormaPagamentoDTO::create));
    }
}
