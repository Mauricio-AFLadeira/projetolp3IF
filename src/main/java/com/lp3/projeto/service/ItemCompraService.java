package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.ItemCompra;
import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.model.repository.ItemCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemCompraService {

    private ItemCompraRepository repository;

    public ItemCompraService(ItemCompraRepository repository){
        this.repository=repository;
    }

    public List<ItemCompra> getPessoaJuridica(){
        return repository.findAll();
    }

    public Optional<ItemCompra> getPessoaJuridicaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public ItemCompra salvar(ItemCompra itemCompra){
        validar(itemCompra);
        return repository.save(itemCompra);
    }

    @Transactional
    public void excluir(ItemCompra itemCompra){
        Objects.requireNonNull(itemCompra);
        repository.delete(itemCompra);
    }

    public void validar(ItemCompra itemCompra){

    }
}
