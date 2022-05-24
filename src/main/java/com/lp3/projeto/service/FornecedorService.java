package com.lp3.projeto.service;

import com.lp3.projeto.model.entity.Fornecedor;
import com.lp3.projeto.model.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    private FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository){
        this.repository=repository;
    }

    public List<Fornecedor> getFornecedor(){
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecdorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor){
        validar(fornecedor);
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor){
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }

    public void validar(Fornecedor fornecedor) {

    }
}
