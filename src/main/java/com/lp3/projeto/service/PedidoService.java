package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Pedido;
import com.lp3.projeto.model.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository repository){
        this.repository=repository;
    }

    public List<Pedido> getPedido(){
        return repository.findAll();
    }

    public Optional<Pedido> getPedidoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Pedido salvar(Pedido pedido){
        validar(pedido);
        return repository.save(pedido);
    }

    @Transactional
    public void excluir(Pedido pedido){
        Objects.requireNonNull(pedido);
        repository.delete(pedido);
    }

    public void validar(Pedido pedido){
        if (pedido.getPessoa().getId()==null){
            throw new RegraNegocioException("Informe qual pessoa o pedido se relaciona");
        }
        if ((pedido.getDataDoPedido()==null||pedido.getDataDoPedido().trim().equals(""))){
            throw new RegraNegocioException("Informe a data na qual o pedido foi realizado");
        }
    }
}
