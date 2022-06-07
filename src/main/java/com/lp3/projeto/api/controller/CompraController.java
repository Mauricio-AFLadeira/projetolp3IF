package com.lp3.projeto.api.controller;


import com.lp3.projeto.api.dto.CompraDTO;
import com.lp3.projeto.model.entity.Compra;
import com.lp3.projeto.service.CompraService;
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
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController {

        private final CompraService service;

        @GetMapping()
        public ResponseEntity get(){
            List<Compra> compras = service.getCompra();
            return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
        }

        @GetMapping("/{id}")
        public ResponseEntity get(@PathVariable("id") Long id){
            Optional<Compra> compra = service.getCompraById(id);
            if(!compra.isPresent()){
                return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(compra.map(CompraDTO::create));
        }
}
