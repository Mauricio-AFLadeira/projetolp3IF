package com.lp3.projeto.api.controller;

import com.lp3.projeto.api.dto.PedidoDTO;
import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Marca;
import com.lp3.projeto.model.entity.Pedido;
import com.lp3.projeto.model.entity.Pessoa;
import com.lp3.projeto.service.PedidoService;
import com.lp3.projeto.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PessoaService pessoaService;

    @GetMapping()
    public ResponseEntity get(){
        List<Pedido> pedidos = service.getPedido();
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()){
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pedido.map(PedidoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(PedidoDTO dto) {
        try {
            Pedido pedido = converter(dto);
            Pessoa pessoa = pessoaService.salvar(pedido.getPessoa());
            pedido.setPessoa(pessoa);
            pedido = service.salvar(pedido);
            return new ResponseEntity(pedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, PedidoDTO dto) {
        if (!service.getPedidoById(id).isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Pedido pedido = converter(dto);
            pedido.setId(id);
            Pessoa pessoa = pessoaService.salvar(pedido.getPessoa());
            pedido.setPessoa(pessoa);
            service.salvar(pedido);
            return ResponseEntity.ok(pedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido =modelMapper.map(dto, Pedido.class);
        if (dto.getIdPessoa() != null) {
            Optional<Pessoa> pessoa = pessoaService.getPessoaById(dto.getIdPessoa());
            if (!pessoa.isPresent()) {
                pedido.setPessoa(null);
            } else {
                pedido.setPessoa(pessoa.get());
            }
        }
        return pedido;
    }
}
