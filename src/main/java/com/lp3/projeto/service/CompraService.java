package com.lp3.projeto.service;

import com.lp3.projeto.exception.RegraNegocioException;
import com.lp3.projeto.model.entity.Compra;
import com.lp3.projeto.model.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraService {

    private CompraRepository repository;

    public CompraService(CompraRepository repository){
        this.repository=repository;
    }

    public List<Compra> getCompra(){
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra){
        validar(compra);
        return repository.save(compra);
    }

    @Transactional
    public void excluir(Compra compra){
        Objects.requireNonNull(compra);
        repository.delete(compra);
    }

    public void validar(Compra compra){

        if (compra.getDataDaCompra()==null){
            throw  new RegraNegocioException("Data da compra não informada");
        }

        if (compra.getFornecedor().getId()==null){
            throw new RegraNegocioException("Fornecedor não relacionado a compra");
        }

    }
}
