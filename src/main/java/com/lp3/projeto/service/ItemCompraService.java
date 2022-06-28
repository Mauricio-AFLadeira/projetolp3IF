package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.ItemCompra;
import com.lp3.projeto.model.entity.PessoaJuridica;
import com.lp3.projeto.model.repository.ItemCompraRepository;
import net.bytebuddy.implementation.bytecode.Throw;
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

    public List<ItemCompra> getItemCompra(){
        return repository.findAll();
    }

    public Optional<ItemCompra> getItemCompraById(Long id){
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

        if (itemCompra.getCompra().getId() == null) {
            throw new RegraNegocioException("Compra não relacionada");
        }
        if (itemCompra.getProduto().getId()==null){
            throw new RegraNegocioException("Produto não relacionado");
        }
        if (itemCompra.getQtdeProduto()==null){
            throw new RegraNegocioException("informe a quantidade de itens comprados do produto");
        }

    }
}
