package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.ItemPedido;
import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.model.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private ItemPedidoRepository repository;

    public ItemPedidoService(ItemPedidoRepository repository){
        this.repository=repository;
    }

    public List<ItemPedido> getItemPedido(){
        return repository.findAll();
    }

    public Optional<ItemPedido> getItemPedidoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public ItemPedido salvar(ItemPedido itemPedido){
        validar(itemPedido);
        return repository.save(itemPedido);
    }

    @Transactional
    public void excluir(ItemPedido itemPedido){
        Objects.requireNonNull(itemPedido);
        repository.delete(itemPedido);
    }

    public void validar(ItemPedido itemPedido){
        if (itemPedido.getPedido().getId() == null) {
            throw new RegraNegocioException("Pedido não relacionado");
        }
        if (itemPedido.getProduto().getId()==null){
            throw new RegraNegocioException("Produto não relacionado");
        }
        if (itemPedido.getQtdeProduto()==null){
            throw new RegraNegocioException("informe a quantidade de itens vendidos do produto");
        }
    }
}

